package com.parquimetro.domain.useCase;

import com.parquimetro.domain.entity.VeiculoEstacionado;

public interface CalcularCobrancaUseCase {
    public VeiculoEstacionado execute(VeiculoEstacionado veiculoEstacionado);
}
