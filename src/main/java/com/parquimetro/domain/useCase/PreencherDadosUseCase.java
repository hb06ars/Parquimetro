package com.parquimetro.domain.useCase;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.parquimetro.domain.dto.VeiculoEstacionadoDTO;

public interface PreencherDadosUseCase {
    public VeiculoEstacionadoDTO execute(VeiculoEstacionadoDTO veiculoEstacionadoDTO) throws JsonProcessingException;
}
