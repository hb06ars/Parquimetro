package com.parquimetro.domain.useCase.impl;

import com.parquimetro.app.service.mongo.VeiculoEstacionadoService;
import com.parquimetro.app.service.redis.VeiculoEstacionadoRedisService;
import com.parquimetro.domain.dto.VeiculoEstacionadoDTO;
import com.parquimetro.domain.entity.VeiculoEstacionado;
import com.parquimetro.domain.enums.StatusPagamentoEnum;
import com.parquimetro.domain.useCase.CalcularCobrancaUseCase;
import com.parquimetro.domain.useCase.PagarVeiculoUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PagarVeiculoUseCaseImpl implements PagarVeiculoUseCase {

    @Autowired
    private CalcularCobrancaUseCase calcularCobrancaUseCase;

    @Autowired
    private VeiculoEstacionadoService veiculoEstacionadoService;

    @Autowired
    private VeiculoEstacionadoRedisService veiculoEstacionadoRedisService;

    @Override
    public VeiculoEstacionadoDTO execute(String placa){
        VeiculoEstacionado veiculoEstacionado = veiculoEstacionadoService.findByPlacaPendentePagamento(placa);
        calcularCobrancaUseCase.execute(veiculoEstacionado);
        veiculoEstacionado.setStatusPagamentoEnum(StatusPagamentoEnum.PAGO);
        veiculoEstacionadoService.save(veiculoEstacionado);
        veiculoEstacionadoRedisService.excluir(placa);
        return new VeiculoEstacionadoDTO(veiculoEstacionado);
    }
}
