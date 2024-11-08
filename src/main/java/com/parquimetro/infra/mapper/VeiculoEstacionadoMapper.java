package com.parquimetro.infra.mapper;

import com.parquimetro.domain.dto.VeiculoEstacionadoDTO;
import com.parquimetro.domain.entity.VeiculoEstacionadoEntity;
import org.springframework.stereotype.Component;

@Component
public class VeiculoEstacionadoMapper {
    public VeiculoEstacionadoDTO toDTO(VeiculoEstacionadoEntity entity) {
        if (entity == null) {
            return null;
        }
        return VeiculoEstacionadoDTO.builder()
                .id(entity.getId())
                .placa(entity.getPlaca())
                .modelo(entity.getModelo())
                .build();
    }

    public VeiculoEstacionadoEntity toEntity(VeiculoEstacionadoDTO dto) {
        if (dto == null) {
            return null;
        }
        return VeiculoEstacionadoEntity.builder()
                .id(dto.getId())
                .placa(dto.getPlaca())
                .modelo(dto.getModelo())
                .build();
    }
}