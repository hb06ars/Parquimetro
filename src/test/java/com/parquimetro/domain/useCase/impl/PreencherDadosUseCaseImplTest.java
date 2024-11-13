package com.parquimetro.domain.useCase.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.parquimetro.app.service.redis.VeiculoEstacionadoRedisService;
import com.parquimetro.domain.dto.VeiculoEstacionadoDTO;
import com.parquimetro.domain.util.GerarNumeroProcesso;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PreencherDadosUseCaseImplTest {

    @InjectMocks
    private PreencherDadosUseCaseImpl preencherDadosUseCase;

    @Mock
    private VeiculoEstacionadoRedisService veiculoEstacionadoRedisService;

    @Mock
    private VeiculoEstacionadoDTO veiculoEstacionadoDTO;

    @Mock
    private GerarNumeroProcesso gerarNumeroProcesso;

    private final String placa = "ABC1234";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testExecute_WhenNumeroProcessoIsNull() throws JsonProcessingException {
        when(veiculoEstacionadoRedisService.encontrarPlacaNaoPaga(placa)).thenReturn(false);
        when(veiculoEstacionadoDTO.getPlaca()).thenReturn(placa);
        when(veiculoEstacionadoDTO.getNumeroProcesso()).thenReturn(null);

        VeiculoEstacionadoDTO result = preencherDadosUseCase.execute(veiculoEstacionadoDTO);

        assertNotNull(result);
        verify(veiculoEstacionadoRedisService, times(1)).encontrarPlacaNaoPaga(placa);
    }

    @Test
    void testExecute_WhenNumeroProcessoIsNotNull() throws JsonProcessingException {
        when(veiculoEstacionadoRedisService.encontrarPlacaNaoPaga(placa)).thenReturn(false);
        when(veiculoEstacionadoDTO.getPlaca()).thenReturn(placa);
        when(veiculoEstacionadoDTO.getNumeroProcesso()).thenReturn("654321");

        VeiculoEstacionadoDTO result = preencherDadosUseCase.execute(veiculoEstacionadoDTO);

        assertNotNull(result);
        assertEquals("654321", result.getNumeroProcesso());
        verify(veiculoEstacionadoRedisService, times(1)).encontrarPlacaNaoPaga(placa);
    }

    @Test
    void testExecute_WhenVeiculoComStatusPendente() {
        when(veiculoEstacionadoRedisService.encontrarPlacaNaoPaga(placa)).thenReturn(true);
        when(veiculoEstacionadoDTO.getPlaca()).thenReturn(placa);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            preencherDadosUseCase.execute(veiculoEstacionadoDTO);
        });

        assertEquals("Veículo com PLACA:ABC1234, está com status PENDENTE.", exception.getMessage());
        verify(veiculoEstacionadoRedisService, times(1)).encontrarPlacaNaoPaga(placa);
    }
}
