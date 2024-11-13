package com.parquimetro.domain.useCase.impl;

import com.parquimetro.app.service.mongo.VeiculoEstacionadoService;
import com.parquimetro.app.service.redis.VeiculoEstacionadoRedisService;
import com.parquimetro.domain.entity.VeiculoEstacionado;
import com.parquimetro.infra.repository.redis.model.VeiculoEstacionadoRedis;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

class DeletarRegistroUseCaseImplTest {

    @InjectMocks
    private DeletarRegistroUseCaseImpl deletarRegistroUseCase;

    @Mock
    private VeiculoEstacionadoRedisService redisService;

    @Mock
    private VeiculoEstacionadoService veiculoEstacionadoService;

    @Mock
    private VeiculoEstacionadoRedis veiculoRedis;

    @Mock
    private VeiculoEstacionado veiculoMongo;

    private final String numeroProcesso = "123456";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testExecute_withRedisAndMongoRecord() {
        when(redisService.findById(numeroProcesso)).thenReturn(veiculoRedis);
        when(veiculoEstacionadoService.findByNumeroProcesso(numeroProcesso)).thenReturn(veiculoMongo);

        deletarRegistroUseCase.execute(numeroProcesso);

        verify(redisService, times(1)).excluir(veiculoRedis.getPlaca());
        verify(veiculoEstacionadoService, times(1)).delete(numeroProcesso);
    }

    @Test
    void testExecute_withOnlyRedisRecord() {
        when(redisService.findById(numeroProcesso)).thenReturn(veiculoRedis);
        when(veiculoEstacionadoService.findByNumeroProcesso(numeroProcesso)).thenReturn(null);

        deletarRegistroUseCase.execute(numeroProcesso);

        verify(redisService, times(1)).excluir(veiculoRedis.getPlaca());
        verify(veiculoEstacionadoService, times(0)).delete(numeroProcesso);
    }

    @Test
    void testExecute_withOnlyMongoRecord() {
        when(redisService.findById(numeroProcesso)).thenReturn(null);
        when(veiculoEstacionadoService.findByNumeroProcesso(numeroProcesso)).thenReturn(veiculoMongo);

        deletarRegistroUseCase.execute(numeroProcesso);

        verify(redisService, times(0)).excluir(anyString());
        verify(veiculoEstacionadoService, times(1)).delete(numeroProcesso);
    }

    @Test
    void testExecute_withNoRecords() {
        when(redisService.findById(numeroProcesso)).thenReturn(null);
        when(veiculoEstacionadoService.findByNumeroProcesso(numeroProcesso)).thenReturn(null);

        deletarRegistroUseCase.execute(numeroProcesso);

        verify(redisService, times(0)).excluir(anyString());
        verify(veiculoEstacionadoService, times(0)).delete(numeroProcesso);
    }
}
