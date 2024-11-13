package com.parquimetro.infra.mapper;

import com.parquimetro.domain.dto.VeiculoEstacionadoDTO;
import com.parquimetro.domain.entity.VeiculoEstacionado;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VeiculoEstacionadoMapperTest {

    private VeiculoEstacionadoMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new VeiculoEstacionadoMapper();
    }

    @Test
    void toDTO_shouldConvertEntityToDTO() {
        VeiculoEstacionado entity = VeiculoEstacionado.builder()
                .id("1")
                .placa("ABC1234")
                .local("Rua 1")
                .build();

        VeiculoEstacionadoDTO dto = mapper.toDTO(entity);

        assertNotNull(dto);
        assertEquals(entity.getId(), dto.getId());
        assertEquals(entity.getPlaca(), dto.getPlaca());
        assertEquals(entity.getLocal(), dto.getLocal());
    }

    @Test
    void toDTO_shouldReturnNullWhenEntityIsNull() {
        assertNull(mapper.toDTO(null));
    }

    @Test
    void toEntity_shouldConvertDTOToEntity() {
        VeiculoEstacionadoDTO dto = VeiculoEstacionadoDTO.builder()
                .id("1")
                .placa("ABC1234")
                .local("Rua 1")
                .build();

        VeiculoEstacionado entity = mapper.toEntity(dto);

        assertNotNull(entity);
        assertEquals(dto.getId(), entity.getId());
        assertEquals(dto.getPlaca(), entity.getPlaca());
        assertEquals(dto.getLocal(), entity.getLocal());
    }

    @Test
    void toEntity_shouldReturnNullWhenDTOIsNull() {
        assertNull(mapper.toEntity(null));
    }
}
