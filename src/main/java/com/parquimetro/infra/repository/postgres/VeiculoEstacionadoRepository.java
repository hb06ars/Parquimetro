package com.parquimetro.infra.repository.postgres;

import com.parquimetro.domain.entity.VeiculoEstacionadoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VeiculoEstacionadoRepository extends JpaRepository<VeiculoEstacionadoEntity, Long> {
}