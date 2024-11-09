package com.parquimetro.domain.useCase.impl;

import com.parquimetro.app.service.mongo.VeiculoEstacionadoService;
import com.parquimetro.domain.dto.VeiculoEstacionadoDTO;
import com.parquimetro.domain.useCase.DevolverVeiculoUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DevolverVeiculoUseCaseImpl implements DevolverVeiculoUseCase {

    @Autowired
    private VeiculoEstacionadoService veiculoEstacionadoService;

    @Override
    public VeiculoEstacionadoDTO execute(String placa){
        return new VeiculoEstacionadoDTO(veiculoEstacionadoService.findByPlacaPendentePagamento(placa));
    }
}
