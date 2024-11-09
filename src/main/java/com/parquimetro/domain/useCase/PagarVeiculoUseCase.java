package com.parquimetro.domain.useCase;

import com.parquimetro.domain.dto.VeiculoEstacionadoDTO;

public interface PagarVeiculoUseCase {
    public VeiculoEstacionadoDTO execute(String placa);
}
