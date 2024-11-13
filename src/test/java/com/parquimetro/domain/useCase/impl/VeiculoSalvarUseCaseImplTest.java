package com.parquimetro.domain.useCase.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.parquimetro.app.service.mongo.VeiculoEstacionadoService;
import com.parquimetro.app.service.redis.VeiculoEstacionadoRedisService;
import com.parquimetro.domain.dto.VeiculoEstacionadoDTO;
import com.parquimetro.domain.entity.VeiculoEstacionado;
import com.parquimetro.domain.enums.StatusPagamentoEnum;
import com.parquimetro.infra.repository.redis.model.VeiculoEstacionadoRedis;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VeiculoSalvarUseCaseImplTest {
    @Mock
    ObjectMapper objectMapper;
    @Mock
    VeiculoEstacionadoService service;
    @Mock
    VeiculoEstacionadoRedisService redisService;
    @InjectMocks
    VeiculoSalvarUseCaseImpl veiculoSalvarUseCaseImpl;


    private String payload;
    private VeiculoEstacionadoDTO veiculoEstacionadoDTO;
    private VeiculoEstacionado veiculoEstacionadoExistente;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Payload de teste e DTO
        payload = "{\"numeroProcesso\":\"12345\", \"placa\":\"ABC1234\", \"horaEntrada\":\"2024-11-12T10:00\", \"horaSaida\":\"2024-11-12T12:00\", \"local\":\"Zona A\", \"valor\":10.5, \"statusPagamentoEnum\":\"PENDENTE\"}";
        veiculoEstacionadoDTO = new VeiculoEstacionadoDTO();
        veiculoEstacionadoDTO.setNumeroProcesso("12345");
        veiculoEstacionadoDTO.setPlaca("ABC1234");
        veiculoEstacionadoDTO.setHoraEntrada(LocalDateTime.parse("2024-11-12T10:00"));
        veiculoEstacionadoDTO.setHoraSaida(LocalDateTime.parse("2024-11-12T12:00"));
        veiculoEstacionadoDTO.setLocal("Zona A");
        veiculoEstacionadoDTO.setValor(new BigDecimal("10.5"));
        veiculoEstacionadoDTO.setStatusPagamentoEnum(StatusPagamentoEnum.PENDENTE_PAGAMENTO);

        veiculoEstacionadoExistente = new VeiculoEstacionado();
        veiculoEstacionadoExistente.setNumeroProcesso("12345");
        veiculoEstacionadoExistente.setPlaca("ABC1234");
    }

    @Test
    void execute_shouldSaveNewVehicleIfNotExists() throws JsonProcessingException {
        // Dados de teste
        String payload = "{\"numeroProcesso\":\"12345\", \"placa\":\"ABC1234\"}";
        VeiculoEstacionadoDTO veiculoEstacionadoDTO = new VeiculoEstacionadoDTO();
        veiculoEstacionadoDTO.setNumeroProcesso("12345");
        veiculoEstacionadoDTO.setPlaca("ABC1234");

        // Mock para retorno dos métodos
        when(objectMapper.readValue(eq(payload), any(TypeReference.class))).thenReturn(veiculoEstacionadoDTO);
        when(service.findByNumeroProcesso("12345")).thenReturn(null);
        VeiculoEstacionado savedVeiculoEstacionado = new VeiculoEstacionado(veiculoEstacionadoDTO);
        savedVeiculoEstacionado.setId(UUID.randomUUID().toString());
        when(service.save(any(VeiculoEstacionado.class))).thenReturn(savedVeiculoEstacionado);

        // Execução do método
        veiculoSalvarUseCaseImpl.execute(payload);

        // Verificações
        verify(service).findByNumeroProcesso("12345");
        verify(service).save(any(VeiculoEstacionado.class));
        verify(redisService).save(any(VeiculoEstacionadoDTO.class));
        assertEquals(savedVeiculoEstacionado.getId(), veiculoEstacionadoDTO.getId());
    }

    @Test
    void execute_shouldUpdateExistingVehicleIfExists() throws JsonProcessingException {
        // Dados de teste
        String payload = "{\"numeroProcesso\":\"12345\", \"placa\":\"ABC1234\"}";
        VeiculoEstacionadoDTO veiculoEstacionadoDTO = new VeiculoEstacionadoDTO();
        veiculoEstacionadoDTO.setNumeroProcesso("12345");
        veiculoEstacionadoDTO.setPlaca("ABC1234");

        VeiculoEstacionado existingVeiculoEstacionado = new VeiculoEstacionado();
        existingVeiculoEstacionado.setId(UUID.randomUUID().toString());
        existingVeiculoEstacionado.setNumeroProcesso("12345");

        // Mock para retorno dos métodos
        when(objectMapper.readValue(eq(payload), any(TypeReference.class))).thenReturn(veiculoEstacionadoDTO);
        when(service.findByNumeroProcesso("12345")).thenReturn(existingVeiculoEstacionado);
        when(service.save(existingVeiculoEstacionado)).thenReturn(existingVeiculoEstacionado);

        // Execução do método
        veiculoSalvarUseCaseImpl.execute(payload);

        // Verificações
        verify(service).findByNumeroProcesso("12345");
        verify(service).save(existingVeiculoEstacionado);
        verify(redisService).save(any(VeiculoEstacionadoDTO.class));
        assertEquals(existingVeiculoEstacionado.getId(), veiculoEstacionadoDTO.getId());
    }

    @Test
    void preencherCampos_shouldCopyFieldsCorrectly() {
        // Dados de teste
        VeiculoEstacionadoDTO veiculoEstacionadoDTO = new VeiculoEstacionadoDTO();
        veiculoEstacionadoDTO.setId("1");
        veiculoEstacionadoDTO.setNumeroProcesso("12345");
        veiculoEstacionadoDTO.setPlaca("DEF5678");

        VeiculoEstacionado veiculoEstacionadoExistente = new VeiculoEstacionado();
        veiculoEstacionadoExistente.setId("1");
        veiculoEstacionadoExistente.setNumeroProcesso("12345");

        // Execução do método
        VeiculoSalvarUseCaseImpl.preencherCampos(veiculoEstacionadoExistente, veiculoEstacionadoDTO);

        // Verificações
        assertEquals(veiculoEstacionadoDTO.getPlaca(), veiculoEstacionadoExistente.getPlaca());
        assertEquals(veiculoEstacionadoDTO.getNumeroProcesso(), veiculoEstacionadoExistente.getNumeroProcesso());
    }

}