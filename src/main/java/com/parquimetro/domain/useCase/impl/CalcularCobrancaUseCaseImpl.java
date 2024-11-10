package com.parquimetro.domain.useCase.impl;

import com.parquimetro.app.service.redis.TarifaRedisService;
import com.parquimetro.domain.entity.VeiculoEstacionado;
import com.parquimetro.domain.useCase.CalcularCobrancaUseCase;
import com.parquimetro.infra.exceptions.ObjectNotFoundException;
import com.parquimetro.infra.repository.redis.model.TarifaRedis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class CalcularCobrancaUseCaseImpl implements CalcularCobrancaUseCase {

    @Autowired
    private TarifaRedisService tarifaRedisService;

    @Override
    public VeiculoEstacionado execute(VeiculoEstacionado veiculoEstacionado) {
        veiculoEstacionado.setHoraSaida(LocalDateTime.now());
        BigDecimal valorTarifa = buscarValorTarifa();
        BigDecimal valorPagar;

        BigDecimal diferencaMinutos = BigDecimal.valueOf(Duration
                .between(veiculoEstacionado.getHoraEntrada(), veiculoEstacionado.getHoraSaida())
                .toMinutes());

        // Cálculo baseado em HORAS. Caso seja menor que 30min cobrará apenas a metade da tarifa.
        if (diferencaMinutos.compareTo(new BigDecimal("30")) <= 0){
            valorPagar = valorTarifa.divide(new BigDecimal("2"), RoundingMode.HALF_UP);
        }
        else{
            BigDecimal diferencaHoras = diferencaMinutos.divide(BigDecimal.valueOf(60), 0, RoundingMode.UP);
            valorPagar = diferencaHoras.multiply(valorTarifa);
        }

        veiculoEstacionado.setValor(valorPagar);
        return veiculoEstacionado;
    }

    private BigDecimal buscarValorTarifa() {
        TarifaRedis tarifaRedis = tarifaRedisService.findFirstTarifa();
        if(tarifaRedis != null && tarifaRedis.getValorTarifa() != null)
            return tarifaRedis.getValorTarifa();
        throw new ObjectNotFoundException("Tarifa não encontrada no sistema");
    }
}
