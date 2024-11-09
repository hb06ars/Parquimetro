package com.parquimetro.domain.useCase.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.parquimetro.domain.dto.VeiculoEstacionadoDTO;
import com.parquimetro.domain.useCase.PreencherDadosUseCase;
import com.parquimetro.domain.util.GerarNumeroProcesso;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class PreencherDadosUseCaseImpl implements PreencherDadosUseCase {

    @Override
    public VeiculoEstacionadoDTO execute(VeiculoEstacionadoDTO veiculoEstacionadoDTO) throws JsonProcessingException {
        if(Objects.isNull(veiculoEstacionadoDTO.getNumeroProcesso()))
            veiculoEstacionadoDTO.setNumeroProcesso(GerarNumeroProcesso.execute(veiculoEstacionadoDTO.getPlaca()));
        return veiculoEstacionadoDTO;
    }
}
