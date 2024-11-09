package com.parquimetro.infra.repository.redis;

import com.parquimetro.infra.repository.redis.model.TarifaRedis;
import org.springframework.data.repository.CrudRepository;

public interface TarifaRedisRepository extends CrudRepository<TarifaRedis, Long> {
}
