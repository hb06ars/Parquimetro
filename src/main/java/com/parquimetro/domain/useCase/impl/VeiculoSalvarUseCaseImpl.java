package com.parquimetro.domain.useCase.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.parquimetro.app.service.mongo.VeiculoEstacionadoService;
import com.parquimetro.domain.dto.VeiculoEstacionadoDTO;
import com.parquimetro.domain.entity.VeiculoEstacionado;
import com.parquimetro.domain.useCase.VeiculoSalvarUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VeiculoSalvarUseCaseImpl implements VeiculoSalvarUseCase {

    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    VeiculoEstacionadoService service;

    @Override
    @Transactional
    public void execute(String payload) throws JsonProcessingException {
        VeiculoEstacionadoDTO veiculoEstacionadoDTO = objectMapper.readValue(payload, new TypeReference<>() {});
        service.save(new VeiculoEstacionado(veiculoEstacionadoDTO));
    }
}
