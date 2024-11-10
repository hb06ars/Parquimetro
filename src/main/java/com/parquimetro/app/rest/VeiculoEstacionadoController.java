package com.parquimetro.app.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.parquimetro.app.service.kafka.producer.KafkaProducer;
import com.parquimetro.app.service.mongo.VeiculoEstacionadoService;
import com.parquimetro.app.service.redis.VeiculoEstacionadoRedisService;
import com.parquimetro.domain.dto.VeiculoEstacionadoDTO;
import com.parquimetro.domain.entity.VeiculoEstacionado;
import com.parquimetro.domain.useCase.DevolverVeiculoUseCase;
import com.parquimetro.domain.useCase.PagarVeiculoUseCase;
import com.parquimetro.domain.useCase.PreencherDadosUseCase;
import com.parquimetro.infra.exceptions.ObjectNotFoundException;
import com.parquimetro.infra.repository.redis.model.VeiculoEstacionadoRedis;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/parquimetro")
@Slf4j
public class VeiculoEstacionadoController {

    @Autowired
    private VeiculoEstacionadoService service;

    @Autowired
    private PreencherDadosUseCase preencherDadosUseCase;

    @Autowired
    private DevolverVeiculoUseCase devolverVeiculoUseCase;

    @Autowired
    private PagarVeiculoUseCase pagarVeiculoUseCase;

    @Autowired
    private VeiculoEstacionadoRedisService veiculoRedisService;

    @Autowired
    private KafkaProducer producer;

    @Autowired
    private ObjectMapper objectMapper;

    @Value("${spring.kafka.topic}")
    private String topico;

    @Operation( summary = "Salva ou Atualiza o veículo no parquímetro",
                description = "Preenche os dados, gerando um número de processo com o padrão: anomesdiahoraminutosegundoplaca -> Exemplo:"
                    + " 20241109205011ASD1234. Após preenchido é enviado uma mensagem e retorna o DTO para o usuário"
                    + " fazenedo com que o usuário não fique aguardando o registro ser salvo."
                    + " Obs: Não é póssível gravar um registro com a mesma placa se houver alguma pendência de pagamento.")
    @ApiResponse(responseCode = "200", description = "Registro salvo/atualizado com sucesso.")
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<VeiculoEstacionadoDTO> saveorupdate(
            @Parameter(description = "Objeto VeiculoEstacionadoDTO.")
            @RequestBody VeiculoEstacionadoDTO veiculoEstacionadoDTO) throws IOException {
        preencherDadosUseCase.execute(veiculoEstacionadoDTO);
        String payload = objectMapper.writeValueAsString(veiculoEstacionadoDTO);
        producer.sendMessage(topico, payload);
        return ResponseEntity.ok(veiculoEstacionadoDTO);
    }

    @Operation( summary = "Busca o Veículo estacionado pelo número do processo",
                description = "Busca o Veículo estacionado pelo número do processo pelo Cache,"
                            + " caso não encontrar o registro ele irá buscar no banco e salvar no Cache")
    @ApiResponse(responseCode = "200", description = "Registro encontrado no sistema.")
    @ApiResponse(responseCode = "404", description = "Registro não encontrado no sistema.")
    @GetMapping("/{numeroProcesso}")
    public ResponseEntity<VeiculoEstacionadoRedis> findByNumeroProcesso(
            @Parameter(description = "Número do processo que é salvo no MongoDB.")
            @PathVariable String numeroProcesso) {
        VeiculoEstacionadoRedis cachedItem = veiculoRedisService.findById(numeroProcesso);
        if (cachedItem == null) {
            VeiculoEstacionado obj = service.findByNumeroProcesso(numeroProcesso);
            if (Objects.nonNull(obj)) {
                cachedItem = new VeiculoEstacionadoRedis(new VeiculoEstacionadoDTO(obj));
                veiculoRedisService.save(new VeiculoEstacionadoDTO(cachedItem));
            }
            throw new ObjectNotFoundException("Objeto não encontrado! Número de processo: " + numeroProcesso);
        }
        return ResponseEntity.ok().body(cachedItem);
    }

    @GetMapping
    @Operation( summary = "Busca todos os Veículos estacionados que estão salvos no cache",
                description = "Busca todos os Veículos estacionados que estão salvos no cache."
                            + " Caso não encontrar, ele irá pesquisar no MongoDB e salvar no Cache")
    @ApiResponse(responseCode = "200", description = "Busca realizada no sistema.")
    public ResponseEntity<Iterable<VeiculoEstacionadoRedis>> findAll() {
        Iterable<VeiculoEstacionadoRedis> cachedItem = veiculoRedisService.findAll();
        long size = StreamSupport.stream(cachedItem.spliterator(), false).count();
        if (size == 0) {
            List<VeiculoEstacionado> lista = service.findAll();
            cachedItem = lista.stream().map(VeiculoEstacionadoRedis::new)
                    .collect(Collectors.toList());
            veiculoRedisService.saveAll(cachedItem);
        }
        return ResponseEntity.ok().body(cachedItem);
    }

    @Operation( summary = "Devolve o objeto Veiculo estacionado com o valor à pagar já calculado",
                description = "Devolve o objeto Veiculo estacionado com o valor à pagar já calculado")
    @ApiResponse(responseCode = "200", description = "Veículo estacionado encontrado e calculado o valor à pagar.")
    @PostMapping("/devolver")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<VeiculoEstacionadoDTO> devolver(
            @Parameter(description = "Número da placa do veículo.")
            @RequestParam(required = true) String placa) throws IOException {
        return ResponseEntity.ok(devolverVeiculoUseCase.execute(placa));
    }

    @Operation( summary = "Pagamento da Parquímetro efetuado",
                description = "Executa o pagamento da Parquímetro e ajusta o Status para PAGO."
                            + " Após o pagameto ser efetuado, o registro será deletado apenas do Cache.")
    @ApiResponse(responseCode = "200", description = "Veículo estacionado pago com sucesso.")
    @PostMapping("/pagar")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<VeiculoEstacionadoDTO> pagar(
            @Parameter(description = "Número da placa do veículo.")
            @RequestParam(required = true) String placa) throws IOException {
        return ResponseEntity.ok(pagarVeiculoUseCase.execute(placa));
    }

}