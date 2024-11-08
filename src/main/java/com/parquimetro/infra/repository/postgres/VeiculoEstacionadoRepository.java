package com.parquimetro.infra.repository.postgres;

import com.parquimetro.domain.entity.VeiculoEstacionado;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface VeiculoEstacionadoRepository extends MongoRepository<VeiculoEstacionado, Long> {
}