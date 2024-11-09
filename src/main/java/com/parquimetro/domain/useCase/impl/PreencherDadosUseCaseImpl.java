package com.parquimetro.domain.useCase.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.parquimetro.app.service.redis.VeiculoEstacionadoRedisService;
import com.parquimetro.domain.dto.VeiculoEstacionadoDTO;
import com.parquimetro.domain.useCase.PreencherDadosUseCase;
import com.parquimetro.domain.util.GerarNumeroProcesso;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class PreencherDadosUseCaseImpl implements PreencherDadosUseCase {

    @Autowired
    private VeiculoEstacionadoRedisService veiculoEstacionadoRedisService;

    @Override
    public VeiculoEstacionadoDTO execute(VeiculoEstacionadoDTO veiculoEstacionadoDTO) throws JsonProcessingException {
        validarVeiculoEStacionado(veiculoEstacionadoDTO.getPlaca());
        if(Objects.isNull(veiculoEstacionadoDTO.getNumeroProcesso()))
            veiculoEstacionadoDTO.setNumeroProcesso(GerarNumeroProcesso.execute(veiculoEstacionadoDTO.getPlaca()));
        return veiculoEstacionadoDTO;
    }

    private void validarVeiculoEStacionado(String placa) {
        if(veiculoEstacionadoRedisService.encontrarPlacaNaoPaga(placa))
            throw new RuntimeException("Veículo com PLACA:" + placa + ", está com status PENDENTE.");
    }
}
