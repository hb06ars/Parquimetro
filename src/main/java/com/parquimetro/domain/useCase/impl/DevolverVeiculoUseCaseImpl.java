package com.parquimetro.domain.useCase.impl;

import com.parquimetro.app.service.mongo.VeiculoEstacionadoService;
import com.parquimetro.domain.dto.VeiculoEstacionadoDTO;
import com.parquimetro.domain.entity.VeiculoEstacionado;
import com.parquimetro.domain.useCase.CalcularCobrancaUseCase;
import com.parquimetro.domain.useCase.DevolverVeiculoUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DevolverVeiculoUseCaseImpl implements DevolverVeiculoUseCase {

    @Autowired
    private CalcularCobrancaUseCase calcularCobrancaUseCase;

    @Autowired
    private VeiculoEstacionadoService veiculoEstacionadoService;

    @Override
    public VeiculoEstacionadoDTO execute(String placa){
        VeiculoEstacionado veiculoEstacionado = veiculoEstacionadoService.findByPlacaPendentePagamento(placa);
        calcularCobrancaUseCase.execute(veiculoEstacionado);
        veiculoEstacionadoService.save(veiculoEstacionado);
        return new VeiculoEstacionadoDTO(veiculoEstacionado);
    }
}
