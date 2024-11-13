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
import java.time.Month;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;

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
        when(preencherDadosUseCase.execute(any(VeiculoEstacionadoDTO.class))).thenReturn(new VeiculoEstacionadoDTO("id", "numeroProcesso", "placa", "local", LocalDateTime.of(2024, Month.NOVEMBER, 12, 23, 8, 30), LocalDateTime.of(2024, Month.NOVEMBER, 12, 23, 8, 30), new BigDecimal(0), StatusPagamentoEnum.PENDENTE_PAGAMENTO));
        when(objectMapper.writeValueAsString(any(Object.class))).thenReturn("writeValueAsStringResponse");

        ResponseEntity<VeiculoEstacionadoDTO> result = veiculoEstacionadoController.saveorupdate(new VeiculoEstacionadoDTO("id", "numeroProcesso", "placa", "local", LocalDateTime.of(2024, Month.NOVEMBER, 12, 23, 8, 30), LocalDateTime.of(2024, Month.NOVEMBER, 12, 23, 8, 30), new BigDecimal(0), StatusPagamentoEnum.PENDENTE_PAGAMENTO));
        verify(producer).sendMessage(any(), anyString());
        Assertions.assertEquals(new ResponseEntity<VeiculoEstacionadoDTO>(new VeiculoEstacionadoDTO("id", "numeroProcesso", "placa", "local", LocalDateTime.of(2024, Month.NOVEMBER, 12, 23, 8, 30), LocalDateTime.of(2024, Month.NOVEMBER, 12, 23, 8, 30), new BigDecimal(0), StatusPagamentoEnum.PENDENTE_PAGAMENTO), null, HttpStatus.OK).getStatusCode(), result.getStatusCode());
    }

    @Test
    void testFindByNumeroProcesso() {
        when(service.findByNumeroProcesso(anyString())).thenReturn(new VeiculoEstacionado("id", "numeroProcesso", "placa", "local", LocalDateTime.of(2024, Month.NOVEMBER, 12, 23, 8, 30), LocalDateTime.of(2024, Month.NOVEMBER, 12, 23, 8, 30), new BigDecimal(0), StatusPagamentoEnum.PENDENTE_PAGAMENTO));
        when(veiculoRedisService.save(any(VeiculoEstacionadoDTO.class))).thenReturn(new VeiculoEstacionadoRedis("numeroProcesso", "placa", "local", LocalDateTime.of(2024, Month.NOVEMBER, 12, 23, 8, 30), LocalDateTime.of(2024, Month.NOVEMBER, 12, 23, 8, 30), new BigDecimal(0), StatusPagamentoEnum.PENDENTE_PAGAMENTO));
        when(veiculoRedisService.findById(anyString())).thenReturn(new VeiculoEstacionadoRedis("numeroProcesso", "placa", "local", LocalDateTime.of(2024, Month.NOVEMBER, 12, 23, 8, 30), LocalDateTime.of(2024, Month.NOVEMBER, 12, 23, 8, 30), new BigDecimal(0), StatusPagamentoEnum.PENDENTE_PAGAMENTO));
        when(veiculoEstacionadoService.findByNumeroProcesso(anyString())).thenReturn(new VeiculoEstacionado("id", "numeroProcesso", "placa", "local", LocalDateTime.of(2024, Month.NOVEMBER, 12, 23, 8, 30), LocalDateTime.of(2024, Month.NOVEMBER, 12, 23, 8, 30), new BigDecimal(0), StatusPagamentoEnum.PENDENTE_PAGAMENTO));

        ResponseEntity<VeiculoEstacionadoRedis> result = veiculoEstacionadoController.findByNumeroProcesso("numeroProcesso");
        Assertions.assertEquals(new ResponseEntity<VeiculoEstacionadoRedis>(new VeiculoEstacionadoRedis("numeroProcesso", "placa", "local", LocalDateTime.of(2024, Month.NOVEMBER, 12, 23, 8, 30), LocalDateTime.of(2024, Month.NOVEMBER, 12, 23, 8, 30), new BigDecimal(0), StatusPagamentoEnum.PENDENTE_PAGAMENTO), null, HttpStatus.OK).getStatusCode(), result.getStatusCode());
    }

    @Test
    void testFindAll() {
        Iterable<VeiculoEstacionadoRedis> cache = Collections.singletonList(VeiculoEstacionadoRedis.builder()
                .horaEntrada(LocalDateTime.now())
                .horaSaida(null)
                .statusPagamentoEnum(StatusPagamentoEnum.PENDENTE_PAGAMENTO)
                .numeroProcesso("sdghg")
                .valor(BigDecimal.ONE)
                .local("A1")
                .placa("ASD").build());
        when(service.findAll()).thenReturn(List.of(new VeiculoEstacionado("id", "numeroProcesso", "placa", "local", LocalDateTime.of(2024, Month.NOVEMBER, 12, 23, 8, 30), LocalDateTime.of(2024, Month.NOVEMBER, 12, 23, 8, 30), new BigDecimal(0), StatusPagamentoEnum.PENDENTE_PAGAMENTO)));
        when(veiculoRedisService.saveAll(any(Iterable.class))).thenReturn(cache);
        when(veiculoRedisService.findAll()).thenReturn(cache);
        when(veiculoEstacionadoService.findAll()).thenReturn(List.of(new VeiculoEstacionado("id", "numeroProcesso", "placa", "local", LocalDateTime.of(2024, Month.NOVEMBER, 12, 23, 8, 30), LocalDateTime.of(2024, Month.NOVEMBER, 12, 23, 8, 30), new BigDecimal(0), StatusPagamentoEnum.PENDENTE_PAGAMENTO)));

        ResponseEntity<Iterable<VeiculoEstacionadoRedis>> result = veiculoEstacionadoController.findAll();
        Assertions.assertEquals(new ResponseEntity<Iterable<VeiculoEstacionadoRedis>>(null, null, HttpStatus.OK).getStatusCode(), result.getStatusCode());
    }

    @Test
    void testDevolver() throws IOException {
        when(devolverVeiculoUseCase.execute(anyString())).thenReturn(new VeiculoEstacionadoDTO("id", "numeroProcesso", "placa", "local", LocalDateTime.of(2024, Month.NOVEMBER, 12, 23, 8, 30), LocalDateTime.of(2024, Month.NOVEMBER, 12, 23, 8, 30), new BigDecimal(0), StatusPagamentoEnum.PENDENTE_PAGAMENTO));

        ResponseEntity<VeiculoEstacionadoDTO> result = veiculoEstacionadoController.devolver("placa");
        Assertions.assertEquals(new ResponseEntity<VeiculoEstacionadoDTO>(new VeiculoEstacionadoDTO("id", "numeroProcesso", "placa", "local", LocalDateTime.of(2024, Month.NOVEMBER, 12, 23, 8, 30), LocalDateTime.of(2024, Month.NOVEMBER, 12, 23, 8, 30), new BigDecimal(0), StatusPagamentoEnum.PENDENTE_PAGAMENTO), null, HttpStatus.OK).getStatusCode(), result.getStatusCode());
    }

    @Test
    void testPagar() throws IOException {
        when(pagarVeiculoUseCase.execute(anyString())).thenReturn(new VeiculoEstacionadoDTO("id", "numeroProcesso", "placa", "local", LocalDateTime.of(2024, Month.NOVEMBER, 12, 23, 8, 30), LocalDateTime.of(2024, Month.NOVEMBER, 12, 23, 8, 30), new BigDecimal(0), StatusPagamentoEnum.PENDENTE_PAGAMENTO));

        ResponseEntity<VeiculoEstacionadoDTO> result = veiculoEstacionadoController.pagar("placa");
        Assertions.assertEquals(new ResponseEntity<VeiculoEstacionadoDTO>(new VeiculoEstacionadoDTO("id", "numeroProcesso", "placa", "local", LocalDateTime.of(2024, Month.NOVEMBER, 12, 23, 8, 30), LocalDateTime.of(2024, Month.NOVEMBER, 12, 23, 8, 30), new BigDecimal(0), StatusPagamentoEnum.PENDENTE_PAGAMENTO), null, HttpStatus.OK).getStatusCode(), result.getStatusCode());
    }

    @Test
    void testBuscaPaginada() {
        when(service.buscaPaginada(any(RequestVeiculoEstacionadoDTO.class), anyInt(), anyInt(), anyString(), anyString())).thenReturn(null);
        when(veiculoEstacionadoService.buscaPaginada(any(RequestVeiculoEstacionadoDTO.class), anyInt(), anyInt(), anyString(), anyString())).thenReturn(null);

        ResponseEntity<Page<VeiculoEstacionado>> result = veiculoEstacionadoController.buscaPaginada(new RequestVeiculoEstacionadoDTO("numeroProcesso", "placa", "local", LocalDateTime.of(2024, Month.NOVEMBER, 12, 23, 8, 30), StatusPagamentoEnum.PENDENTE_PAGAMENTO), 0, 0, "sortField", "sortDirection");
        Assertions.assertEquals(new ResponseEntity<Page<VeiculoEstacionado>>(null, null, HttpStatus.OK).getStatusCode(), result.getStatusCode());
    }

    @Test
    void testDeletar() {
        veiculoEstacionadoController.deletar("numeroProcesso");
        verify(deletarRegistroUseCase).execute(anyString());
    }
}

//Generated with love by TestMe :) Please raise issues & feature requests at: https://weirddev.com/forum#!/testme