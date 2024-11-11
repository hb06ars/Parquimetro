package com.parquimetro.domain.useCase.impl;

import com.parquimetro.app.service.mongo.VeiculoEstacionadoService;
import com.parquimetro.app.service.redis.VeiculoEstacionadoRedisService;
import com.parquimetro.domain.entity.VeiculoEstacionado;
import com.parquimetro.domain.useCase.DeletarRegistroUseCase;
import com.parquimetro.infra.repository.redis.model.VeiculoEstacionadoRedis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DeletarRegistroUseCaseImpl implements DeletarRegistroUseCase {

    @Autowired
    private VeiculoEstacionadoRedisService redisService;

    @Autowired
    private VeiculoEstacionadoService veiculoEstacionadoService;

    @Override
    @Transactional
    public void execute(String numeroProcesso) {
        VeiculoEstacionadoRedis veiculoRedis = redisService.findById(numeroProcesso);
        VeiculoEstacionado veiculoMongo = veiculoEstacionadoService.findByNumeroProcesso(numeroProcesso);
        if(veiculoRedis != null)
            redisService.excluir(veiculoRedis.getPlaca());
        if(veiculoMongo != null)
            veiculoEstacionadoService.delete(numeroProcesso);
    }
}
