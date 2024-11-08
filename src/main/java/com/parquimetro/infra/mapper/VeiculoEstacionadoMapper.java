package com.parquimetro.infra.mapper;

import com.parquimetro.domain.dto.VeiculoEstacionadoDTO;
import com.parquimetro.domain.entity.VeiculoEstacionado;
import org.springframework.stereotype.Component;

@Component
public class VeiculoEstacionadoMapper {
    public VeiculoEstacionadoDTO toDTO(VeiculoEstacionado entity) {
        if (entity == null) {
            return null;
        }
        return VeiculoEstacionadoDTO.builder()
                .id(entity.getId())
                .placa(entity.getPlaca())
                .modelo(entity.getModelo())
                .build();
    }

    public VeiculoEstacionado toEntity(VeiculoEstacionadoDTO dto) {
        if (dto == null) {
            return null;
        }
        return VeiculoEstacionado.builder()
                .id(dto.getId())
                .placa(dto.getPlaca())
                .modelo(dto.getModelo())
                .build();
    }
}