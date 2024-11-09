package com.parquimetro.app.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.parquimetro.app.service.kafka.producer.KafkaProducer;
import com.parquimetro.app.service.mongo.VeiculoEstacionadoService;
import com.parquimetro.app.service.redis.VeiculoEstacionadoRedisService;
import com.parquimetro.domain.dto.VeiculoEstacionadoDTO;
import com.parquimetro.domain.entity.VeiculoEstacionado;
import com.parquimetro.domain.useCase.DevolverVeiculoUseCase;
import com.parquimetro.domain.useCase.PreencherDadosUseCase;
import com.parquimetro.infra.exceptions.ObjectNotFoundException;
import com.parquimetro.infra.repository.redis.model.VeiculoEstacionadoRedis;
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
public class ParquimetroController {

    @Autowired
    private VeiculoEstacionadoService service;

    @Autowired
    private PreencherDadosUseCase preencherDadosUseCase;

    @Autowired
    private DevolverVeiculoUseCase devolverVeiculoUseCase;

    @Autowired
    private VeiculoEstacionadoRedisService redisService;

    @Autowired
    private KafkaProducer producer;

    @Autowired
    private ObjectMapper objectMapper;

    @Value("${spring.kafka.topic}")
    private String topico;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<VeiculoEstacionadoDTO> saveorupdate(@RequestBody VeiculoEstacionadoDTO veiculoEstacionadoDTO) throws IOException {
        preencherDadosUseCase.execute(veiculoEstacionadoDTO);
        String payload = objectMapper.writeValueAsString(veiculoEstacionadoDTO);
        producer.sendMessage(topico, payload);
        return ResponseEntity.ok(veiculoEstacionadoDTO);
    }

    @GetMapping("/{numeroProcesso}")
    public ResponseEntity<VeiculoEstacionadoRedis> findByNumeroProcesso(@PathVariable String numeroProcesso) {
        VeiculoEstacionadoRedis cachedItem = redisService.findById(numeroProcesso);
        if (cachedItem == null) {
            VeiculoEstacionado obj = service.findByNumeroProcesso(numeroProcesso);
            if(Objects.nonNull(obj)){
                cachedItem = new VeiculoEstacionadoRedis(new VeiculoEstacionadoDTO(obj));
                redisService.save(new VeiculoEstacionadoDTO(cachedItem));
            }
            throw new ObjectNotFoundException("Objeto não encontrado! Número de processo: " + numeroProcesso);
        }
        return ResponseEntity.ok().body(cachedItem);
    }

    @GetMapping
    public ResponseEntity<Iterable<VeiculoEstacionadoRedis>> findAll() {
        Iterable<VeiculoEstacionadoRedis> cachedItem = redisService.findAll();
        long size = StreamSupport.stream(cachedItem.spliterator(), false).count();
        if (size == 0) {
            List<VeiculoEstacionado> lista = service.findAll();
            cachedItem = lista.stream().map(VeiculoEstacionadoRedis::new)
                    .collect(Collectors.toList());
            redisService.saveAll(cachedItem);
        }
        return ResponseEntity.ok().body(cachedItem);
    }


    @PostMapping("/devolver")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<VeiculoEstacionadoDTO> devolver(@RequestParam(required = true) String placa) throws IOException {
        return ResponseEntity.ok(devolverVeiculoUseCase.execute(placa));
    }
}