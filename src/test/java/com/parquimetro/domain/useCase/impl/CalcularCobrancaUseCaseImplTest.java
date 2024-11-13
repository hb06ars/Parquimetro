package com.parquimetro.domain.useCase.impl;

import com.parquimetro.app.service.redis.TarifaRedisService;
import com.parquimetro.domain.entity.VeiculoEstacionado;
import com.parquimetro.infra.exceptions.ObjectNotFoundException;
import com.parquimetro.infra.repository.redis.model.TarifaRedis;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class CalcularCobrancaUseCaseImplTest {

    @InjectMocks
    private CalcularCobrancaUseCaseImpl calcularCobrancaUseCase;

    @Mock
    private TarifaRedisService tarifaRedisService;

    @Mock
    private TarifaRedis tarifaRedis;

    private VeiculoEstacionado veiculoEstacionado;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        veiculoEstacionado = new VeiculoEstacionado();
        veiculoEstacionado.setHoraEntrada(LocalDateTime.now().minusMinutes(45)); // 45 minutos atrás
        veiculoEstacionado.setPlaca("ABC1234");
    }

    @Test
    void testExecute_withLessThan30Minutes() {
        // Mockando o serviço de tarifa
        when(tarifaRedisService.findFirstTarifa()).thenReturn(tarifaRedis);
        when(tarifaRedis.getValorTarifa()).thenReturn(new BigDecimal("5.00"));

        // Definindo hora de saída
        veiculoEstacionado.setHoraEntrada(LocalDateTime.now().minusMinutes(20)); // 45 minutos atrás
        veiculoEstacionado.setHoraSaida(LocalDateTime.now());

        // Executando o método
        VeiculoEstacionado result = calcularCobrancaUseCase.execute(veiculoEstacionado);

        // Verificando que o valor cobrado é a metade da tarifa
        assertEquals(new BigDecimal("2.50"), result.getValor());
    }

    @Test
    void testExecute_withMoreThan30Minutes() {
        // Mockando o serviço de tarifa
        when(tarifaRedisService.findFirstTarifa()).thenReturn(tarifaRedis);
        when(tarifaRedis.getValorTarifa()).thenReturn(new BigDecimal("5.00"));

        // Definindo hora de saída
        veiculoEstacionado.setHoraSaida(LocalDateTime.now());

        // Executando o método
        VeiculoEstacionado result = calcularCobrancaUseCase.execute(veiculoEstacionado);

        // Verificando que o valor cobrado é proporcional ao tempo de estacionamento
        assertEquals(new BigDecimal("5.00"), result.getValor());
    }

    @Test
    void testExecute_tarifaNotFound() {
        // Mockando o serviço de tarifa
        when(tarifaRedisService.findFirstTarifa()).thenReturn(null);

        // Executando o método e verificando que o erro é lançado
        assertThrows(ObjectNotFoundException.class, () -> calcularCobrancaUseCase.execute(veiculoEstacionado));
    }

    @Test
    void testBuscarValorTarifa_tarifaFound() {
        // Mockando o serviço de tarifa
        when(tarifaRedisService.findFirstTarifa()).thenReturn(tarifaRedis);
        when(tarifaRedis.getValorTarifa()).thenReturn(new BigDecimal("10.00"));

        // Testando a busca do valor da tarifa
        BigDecimal valorTarifa = calcularCobrancaUseCase.buscarValorTarifa();

        // Verificando se o valor retornado é correto
        assertEquals(new BigDecimal("10.00"), valorTarifa);
    }

    @Test
    void testBuscarValorTarifa_tarifaNotFound() {
        // Mockando o serviço de tarifa
        when(tarifaRedisService.findFirstTarifa()).thenReturn(null);

        // Verificando se a exceção é lançada
        assertThrows(ObjectNotFoundException.class, () -> calcularCobrancaUseCase.buscarValorTarifa());
    }
}
