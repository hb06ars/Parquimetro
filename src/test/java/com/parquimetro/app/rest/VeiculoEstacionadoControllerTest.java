package com.parquimetro.app.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.parquimetro.app.service.kafka.producer.KafkaProducer;
import com.parquimetro.app.service.mongo.VeiculoEstacionadoService;
import com.parquimetro.app.service.redis.VeiculoEstacionadoRedisService;
import com.parquimetro.domain.dto.RequestVeiculoEstacionadoDTO;
import com.parquimetro.domain.dto.VeiculoEstacionadoDTO;
import com.parquimetro.domain.entity.VeiculoEstacionado;
import com.parquimetro.domain.enums.StatusPagamentoEnum;
import com.parquimetro.domain.useCase.DeletarRegistroUseCase;
import com.parquimetro.domain.useCase.DevolverVeiculoUseCase;
import com.parquimetro.domain.useCase.PagarVeiculoUseCase;
import com.parquimetro.domain.useCase.PreencherDadosUseCase;
import com.parquimetro.infra.repository.redis.model.VeiculoEstacionadoRedis;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class VeiculoEstacionadoControllerTest {
    @Mock
    VeiculoEstacionadoService service;
    @Mock
    PreencherDadosUseCase preencherDadosUseCase;
    @Mock
    DevolverVeiculoUseCase devolverVeiculoUseCase;
    @Mock
    PagarVeiculoUseCase pagarVeiculoUseCase;
    @Mock
    DeletarRegistroUseCase deletarRegistroUseCase;
    @Mock
    VeiculoEstacionadoRedisService veiculoRedisService;
    @Mock
    VeiculoEstacionadoService veiculoEstacionadoService;
    @Mock
    KafkaProducer producer;
    @Mock
    ObjectMapper objectMapper;
    @InjectMocks
    VeiculoEstacionadoController veiculoEstacionadoController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveorupdate() throws IOException {
        VeiculoEstacionadoDTO veiculoEstacionadoDTO = new VeiculoEstacionadoDTO(gerarVeiculoEstacionado());
        when(preencherDadosUseCase.execute(any(VeiculoEstacionadoDTO.class))).thenReturn(veiculoEstacionadoDTO);
        when(objectMapper.writeValueAsString(any(Object.class))).thenReturn("writeValueAsStringResponse");

        ResponseEntity<VeiculoEstacionadoDTO> result = veiculoEstacionadoController.saveorupdate(veiculoEstacionadoDTO);
        verify(producer).sendMessage(any(), anyString());
        Assertions.assertEquals(new ResponseEntity<VeiculoEstacionadoDTO>(veiculoEstacionadoDTO, null, HttpStatus.OK).getStatusCode(), result.getStatusCode());
    }

    @Test
    void testFindByNumeroProcesso() {
        VeiculoEstacionado veiculoEstacionado = gerarVeiculoEstacionado();
        VeiculoEstacionadoRedis veiculoEstacionadoRedis = gerarVeiculoEstacionadoRedis();

        when(service.findByNumeroProcesso(anyString())).thenReturn(veiculoEstacionado);
        when(veiculoRedisService.save(any(VeiculoEstacionadoDTO.class))).thenReturn(veiculoEstacionadoRedis);
        when(veiculoRedisService.findById(anyString())).thenReturn(veiculoEstacionadoRedis);
        when(veiculoEstacionadoService.findByNumeroProcesso(anyString())).thenReturn(veiculoEstacionado);

        ResponseEntity<VeiculoEstacionadoRedis> result = veiculoEstacionadoController.findByNumeroProcesso("numeroProcesso");
        Assertions.assertEquals(new ResponseEntity<VeiculoEstacionadoRedis>(veiculoEstacionadoRedis, null, HttpStatus.OK).getStatusCode(), result.getStatusCode());

    }

    @Test
    void testFindAll() {
        VeiculoEstacionado veiculoEstacionado = gerarVeiculoEstacionado();
        Iterable<VeiculoEstacionadoRedis> cache = Collections.singletonList(VeiculoEstacionadoRedis.builder()
                .horaEntrada(LocalDateTime.now())
                .horaSaida(null)
                .statusPagamentoEnum(StatusPagamentoEnum.PENDENTE_PAGAMENTO)
                .numeroProcesso("sdghg")
                .valor(BigDecimal.ONE)
                .local("A1")
                .placa("ASD").build());
        when(service.findAll()).thenReturn(List.of(veiculoEstacionado));
        when(veiculoRedisService.saveAll(any(Iterable.class))).thenReturn(cache);
        when(veiculoRedisService.findAll()).thenReturn(cache);
        when(veiculoEstacionadoService.findAll()).thenReturn(List.of(veiculoEstacionado));

        ResponseEntity<Iterable<VeiculoEstacionadoRedis>> result = veiculoEstacionadoController.findAll();
        Assertions.assertEquals(new ResponseEntity<Iterable<VeiculoEstacionadoRedis>>(null, null, HttpStatus.OK).getStatusCode(), result.getStatusCode());
    }

    @Test
    void testDevolver() throws IOException {
        VeiculoEstacionadoDTO veiculoEstacionadoDTO = new VeiculoEstacionadoDTO(gerarVeiculoEstacionado());
        when(devolverVeiculoUseCase.execute(anyString())).thenReturn(veiculoEstacionadoDTO);

        ResponseEntity<VeiculoEstacionadoDTO> result = veiculoEstacionadoController.devolver("placa");
        Assertions.assertEquals(new ResponseEntity<VeiculoEstacionadoDTO>(veiculoEstacionadoDTO, null, HttpStatus.OK).getStatusCode(), result.getStatusCode());
    }

    @Test
    void testPagar() throws IOException {
        VeiculoEstacionadoDTO veiculoEstacionadoDTO = new VeiculoEstacionadoDTO(gerarVeiculoEstacionado());
        when(pagarVeiculoUseCase.execute(anyString())).thenReturn(veiculoEstacionadoDTO);

        ResponseEntity<VeiculoEstacionadoDTO> result = veiculoEstacionadoController.pagar("placa");
        Assertions.assertEquals(new ResponseEntity<VeiculoEstacionadoDTO>(veiculoEstacionadoDTO, null, HttpStatus.OK).getStatusCode(), result.getStatusCode());
    }

    @Test
    void testBuscaPaginada() {
        RequestVeiculoEstacionadoDTO requestVeiculoEstacionadoDTO = new RequestVeiculoEstacionadoDTO();
        requestVeiculoEstacionadoDTO.setNumeroProcesso(UUID.randomUUID().toString());
        requestVeiculoEstacionadoDTO.setPlaca("ASW1455");
        requestVeiculoEstacionadoDTO.setLocal("A10");
        requestVeiculoEstacionadoDTO.setHorario(LocalDateTime.now());
        requestVeiculoEstacionadoDTO.setStatusPagamentoEnum(StatusPagamentoEnum.PENDENTE_PAGAMENTO);

        when(service.buscaPaginada(any(RequestVeiculoEstacionadoDTO.class), anyInt(), anyInt(), anyString(), anyString())).thenReturn(null);
        when(veiculoEstacionadoService.buscaPaginada(any(RequestVeiculoEstacionadoDTO.class), anyInt(), anyInt(), anyString(), anyString())).thenReturn(null);

        ResponseEntity<Page<VeiculoEstacionado>> result = veiculoEstacionadoController.buscaPaginada(requestVeiculoEstacionadoDTO, 0, 0, "sortField", "sortDirection");
        Assertions.assertEquals(new ResponseEntity<Page<VeiculoEstacionado>>(null, null, HttpStatus.OK).getStatusCode(), result.getStatusCode());
    }

    @Test
    void testDeletar() {
        veiculoEstacionadoController.deletar("numeroProcesso");
        verify(deletarRegistroUseCase).execute(anyString());
    }

    VeiculoEstacionado gerarVeiculoEstacionado() {
        VeiculoEstacionado veiculoEstacionado = new VeiculoEstacionado();
        veiculoEstacionado.setId("asdadfretert23423");
        veiculoEstacionado.setNumeroProcesso(UUID.randomUUID().toString());
        veiculoEstacionado.setPlaca("AVC5422");
        veiculoEstacionado.setLocal("A10");
        veiculoEstacionado.setStatusPagamentoEnum(StatusPagamentoEnum.PENDENTE_PAGAMENTO);
        veiculoEstacionado.setValor(BigDecimal.valueOf(10L));
        veiculoEstacionado.setHoraEntrada(LocalDateTime.now());
        veiculoEstacionado.setHoraSaida(null);
        return veiculoEstacionado;
    }

    VeiculoEstacionadoRedis gerarVeiculoEstacionadoRedis() {
        VeiculoEstacionadoRedis veiculoEstacionado = new VeiculoEstacionadoRedis();
        veiculoEstacionado.setNumeroProcesso(UUID.randomUUID().toString());
        veiculoEstacionado.setPlaca("AVC5422");
        veiculoEstacionado.setLocal("A10");
        veiculoEstacionado.setStatusPagamentoEnum(StatusPagamentoEnum.PENDENTE_PAGAMENTO);
        veiculoEstacionado.setValor(BigDecimal.valueOf(10L));
        veiculoEstacionado.setHoraEntrada(LocalDateTime.now());
        veiculoEstacionado.setHoraSaida(null);
        return veiculoEstacionado;
    }


}

