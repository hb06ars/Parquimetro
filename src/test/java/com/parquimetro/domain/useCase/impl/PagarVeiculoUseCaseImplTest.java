package com.parquimetro.domain.useCase.impl;

import com.parquimetro.app.service.mongo.VeiculoEstacionadoService;
import com.parquimetro.app.service.redis.VeiculoEstacionadoRedisService;
import com.parquimetro.domain.dto.VeiculoEstacionadoDTO;
import com.parquimetro.domain.entity.VeiculoEstacionado;
import com.parquimetro.domain.enums.StatusPagamentoEnum;
import com.parquimetro.domain.useCase.CalcularCobrancaUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PagarVeiculoUseCaseImplTest {

    @InjectMocks
    private PagarVeiculoUseCaseImpl pagarVeiculoUseCase;

    @Mock
    private CalcularCobrancaUseCase calcularCobrancaUseCase;

    @Mock
    private VeiculoEstacionadoService veiculoEstacionadoService;

    @Mock
    private VeiculoEstacionadoRedisService veiculoEstacionadoRedisService;

    @Mock
    private VeiculoEstacionadoDTO veiculoEstacionadoDTO;

    private final String placa = "ABC1234";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testExecute() {
        VeiculoEstacionado veiculoEstacionado = VeiculoEstacionado.builder()
                .statusPagamentoEnum(StatusPagamentoEnum.PAGO)
                .build();
        veiculoEstacionado.setStatusPagamentoEnum(StatusPagamentoEnum.PAGO);
        when(veiculoEstacionadoService.findByPlacaPendentePagamento(placa)).thenReturn(veiculoEstacionado);
        when(calcularCobrancaUseCase.execute(veiculoEstacionado)).thenReturn(veiculoEstacionado);
        when(veiculoEstacionadoService.save(veiculoEstacionado)).thenReturn(veiculoEstacionado);

        VeiculoEstacionadoDTO result = pagarVeiculoUseCase.execute(placa);

        verify(veiculoEstacionadoService, times(1)).findByPlacaPendentePagamento(placa);
        verify(calcularCobrancaUseCase, times(1)).execute(veiculoEstacionado);
        verify(veiculoEstacionadoService, times(1)).save(veiculoEstacionado);
        verify(veiculoEstacionadoRedisService, times(1)).excluir(placa);
        verifyNoMoreInteractions(veiculoEstacionadoService, calcularCobrancaUseCase, veiculoEstacionadoRedisService);

        assertNotNull(result);
        assertEquals(StatusPagamentoEnum.PAGO, veiculoEstacionado.getStatusPagamentoEnum());
    }

    @Test
    void testExecute_WhenVeiculoNotFound() {
        VeiculoEstacionado veiculoEstacionado = VeiculoEstacionado.builder()
                .build();

        when(veiculoEstacionadoService.findByPlacaPendentePagamento(placa)).thenReturn(veiculoEstacionado);

        VeiculoEstacionadoDTO result = pagarVeiculoUseCase.execute(placa);

        verify(veiculoEstacionadoService, times(1)).findByPlacaPendentePagamento(placa);
        verify(calcularCobrancaUseCase, times(1)).execute(any());
        verify(veiculoEstacionadoService, times(1)).save(any());
        verify(veiculoEstacionadoRedisService, times(1)).excluir(any());

        assertNull(result.getId());
    }
}
