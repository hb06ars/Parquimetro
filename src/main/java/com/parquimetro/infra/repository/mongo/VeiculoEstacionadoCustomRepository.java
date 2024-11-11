package com.parquimetro.infra.repository.mongo;

import com.parquimetro.domain.dto.RequestVeiculoEstacionadoDTO;
import com.parquimetro.domain.entity.VeiculoEstacionado;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface VeiculoEstacionadoCustomRepository{
    Page<VeiculoEstacionado> findAllByCriteria(RequestVeiculoEstacionadoDTO dto, Pageable pageable, String sortField, String sortDirection);
}