package com.parquimetro.infra.repository.redis;

import com.parquimetro.infra.repository.redis.model.VeiculoEstacionadoRedis;
import org.springframework.data.repository.CrudRepository;

public interface VeiculoEstacionadoRedisRepository extends CrudRepository<VeiculoEstacionadoRedis, String> {
}
