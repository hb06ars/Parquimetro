package com.parquimetro.domain.useCase;

import com.parquimetro.domain.dto.VeiculoEstacionadoDTO;

public interface DevolverVeiculoUseCase {
    public VeiculoEstacionadoDTO execute(String placa);
}
