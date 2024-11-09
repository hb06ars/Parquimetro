package com.parquimetro.domain.useCase.impl;

import com.parquimetro.domain.entity.VeiculoEstacionado;
import com.parquimetro.domain.useCase.CalcularCobrancaUseCase;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class CalcularCobrancaUseCaseImpl implements CalcularCobrancaUseCase {

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
        return BigDecimal.TEN;
    }
}
