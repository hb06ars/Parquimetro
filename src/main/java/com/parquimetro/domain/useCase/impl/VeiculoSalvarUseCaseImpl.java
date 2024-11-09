package com.parquimetro.domain.useCase.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.parquimetro.app.service.mongo.VeiculoEstacionadoService;
import com.parquimetro.app.service.redis.VeiculoEstacionadoRedisService;
import com.parquimetro.domain.dto.VeiculoEstacionadoDTO;
import com.parquimetro.domain.entity.VeiculoEstacionado;
import com.parquimetro.domain.useCase.VeiculoSalvarUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
public class VeiculoSalvarUseCaseImpl implements VeiculoSalvarUseCase {

    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    VeiculoEstacionadoService service;
    @Autowired
    VeiculoEstacionadoRedisService redisService;

    @Override
    @Transactional
    public void execute(String payload) throws JsonProcessingException {
        VeiculoEstacionadoDTO veiculoEstacionadoDTO = objectMapper.readValue(payload, new TypeReference<>() {});
        VeiculoEstacionado veiculoEstacionadoExistente = service.findByNumeroProcesso(veiculoEstacionadoDTO.getNumeroProcesso());
        if(Objects.nonNull(veiculoEstacionadoExistente))
            preencherCampos(veiculoEstacionadoExistente, veiculoEstacionadoDTO);
        else
            veiculoEstacionadoExistente = new VeiculoEstacionado(veiculoEstacionadoDTO);

        var result = service.save(veiculoEstacionadoExistente);
        veiculoEstacionadoDTO.setId(result.getId());
        redisService.save(veiculoEstacionadoDTO);
    }

    private static void preencherCampos(VeiculoEstacionado veiculoEstacionadoExistente, VeiculoEstacionadoDTO veiculoEstacionadoDTO) {
        veiculoEstacionadoDTO.setId(veiculoEstacionadoExistente.getId());
        veiculoEstacionadoDTO.setNumeroProcesso(veiculoEstacionadoExistente.getNumeroProcesso());

        veiculoEstacionadoExistente.setHoraEntrada(veiculoEstacionadoDTO.getHoraEntrada());
        veiculoEstacionadoExistente.setHoraSaida(veiculoEstacionadoDTO.getHoraSaida());
        veiculoEstacionadoExistente.setPlaca(veiculoEstacionadoDTO.getPlaca());
        veiculoEstacionadoExistente.setLocal(veiculoEstacionadoDTO.getLocal());
        veiculoEstacionadoExistente.setValor(veiculoEstacionadoDTO.getValor());
        veiculoEstacionadoExistente.setStatusPagamentoEnum(veiculoEstacionadoDTO.getStatusPagamentoEnum());
    }
}
