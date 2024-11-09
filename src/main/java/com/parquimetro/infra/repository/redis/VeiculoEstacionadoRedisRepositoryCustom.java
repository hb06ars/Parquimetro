package com.parquimetro.infra.repository.redis;

import com.parquimetro.infra.repository.redis.model.VeiculoEstacionadoRedis;

import java.util.List;

public interface VeiculoEstacionadoRedisRepositoryCustom {
    boolean encontrarPlacaNaoPaga(String placa);
}
