package com.parquimetro.domain.useCase;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface VeiculoSalvarUseCase {
    public void execute(String payload) throws JsonProcessingException;
}
