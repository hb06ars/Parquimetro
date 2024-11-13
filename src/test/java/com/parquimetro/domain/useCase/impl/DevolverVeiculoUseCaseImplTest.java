package com.parquimetro.domain.useCase.impl;

import com.parquimetro.app.service.mongo.VeiculoEstacionadoService;
import com.parquimetro.domain.dto.VeiculoEstacionadoDTO;
import com.parquimetro.domain.entity.VeiculoEstacionado;
import com.parquimetro.domain.useCase.CalcularCobrancaUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DevolverVeiculoUseCaseImplTest {

    @InjectMocks
    private DevolverVeiculoUseCaseImpl devolverVeiculoUseCase;

    @Mock
    private CalcularCobrancaUseCase calcularCobrancaUseCase;

    @Mock
    private VeiculoEstacionadoService veiculoEstacionadoService;

    @Mock
    private VeiculoEstacionado veiculoEstacionado;

    @Mock
    private VeiculoEstacionadoDTO veiculoEstacionadoDTO;

    private final String placa = "ABC1234";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testExecute() {
        when(veiculoEstacionadoService.findByPlacaPendentePagamento(placa)).thenReturn(veiculoEstacionado);
        when(calcularCobrancaUseCase.execute(veiculoEstacionado)).thenReturn(veiculoEstacionado);

        VeiculoEstacionadoDTO result = devolverVeiculoUseCase.execute(placa);

        verify(veiculoEstacionadoService, times(1)).findByPlacaPendentePagamento(placa);
        verify(calcularCobrancaUseCase, times(1)).execute(veiculoEstacionado);
        verifyNoMoreInteractions(veiculoEstacionadoService, calcularCobrancaUseCase);

        assertNotNull(result);
        assertEquals(veiculoEstacionado.getPlaca(), result.getPlaca());
    }

    @Test
    void testExecute_WhenVeiculoNotFound() {
        when(veiculoEstacionadoService.findByPlacaPendentePagamento(placa)).thenReturn(VeiculoEstacionado.builder().build());

        VeiculoEstacionadoDTO result = devolverVeiculoUseCase.execute(placa);

        verify(veiculoEstacionadoService, times(1)).findByPlacaPendentePagamento(placa);
        verify(calcularCobrancaUseCase, times(1)).execute(any());

        assertNull(result.getId());
    }
}
