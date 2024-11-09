package com.parquimetro.domain.useCase.impl;

import com.parquimetro.app.service.redis.TarifaRedisService;
import com.parquimetro.domain.entity.VeiculoEstacionado;
import com.parquimetro.domain.useCase.CalcularCobrancaUseCase;
import com.parquimetro.infra.repository.redis.model.TarifaRedis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class CalcularCobrancaUseCaseImpl implements CalcularCobrancaUseCase {

    @Autowired
    private TarifaRedisService tarifaRedisService;

    @Override
    public VeiculoEstacionado execute(VeiculoEstacionado veiculoEstacionado) {
        veiculoEstacionado.setHoraSaida(LocalDateTime.now());

        BigDecimal diferencaMinutos = BigDecimal.valueOf(Duration
                .between(veiculoEstacionado.getHoraEntrada(), veiculoEstacionado.getHoraSaida())
                .toMinutes());

        veiculoEstacionado.setValor(diferencaMinutos.multiply(buscarValorTarifa()));

        return veiculoEstacionado;
    }

    private BigDecimal buscarValorTarifa() {
        TarifaRedis tarifaRedis = tarifaRedisService.findFirstTarifa();
        return tarifaRedis.getValorTarifa();
    }
}
