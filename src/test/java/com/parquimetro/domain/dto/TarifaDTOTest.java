package com.parquimetro.domain.dto;

import com.parquimetro.infra.repository.redis.model.TarifaRedis;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class TarifaDTOTest {

    @Test
    void testGettersAndSetters() {
        TarifaDTO tarifaDTO = new TarifaDTO();
        tarifaDTO.setId(1L);
        tarifaDTO.setValorTarifa(new BigDecimal("100.00"));

        assertEquals(1L, tarifaDTO.getId());
        assertEquals(new BigDecimal("100.00"), tarifaDTO.getValorTarifa());
    }

    @Test
    void testBuilder() {
        TarifaDTO tarifaDTO = TarifaDTO.builder()
                .id(1L)
                .valorTarifa(new BigDecimal("100.00"))
                .build();

        assertNotNull(tarifaDTO);
        assertEquals(1L, tarifaDTO.getId());
        assertEquals(new BigDecimal("100.00"), tarifaDTO.getValorTarifa());
    }

    @Test
    void testEqualsAndHashCode() {
        TarifaDTO tarifaDTO1 = TarifaDTO.builder()
                .id(1L)
                .valorTarifa(new BigDecimal("100.00"))
                .build();

        TarifaDTO tarifaDTO2 = TarifaDTO.builder()
                .id(1L)
                .valorTarifa(new BigDecimal("100.00"))
                .build();

        assertEquals(tarifaDTO1, tarifaDTO2);
        assertEquals(tarifaDTO1.hashCode(), tarifaDTO2.hashCode());
    }

    @Test
    void testConstructorFromTarifaRedis() {
        TarifaRedis tarifaRedis = new TarifaRedis();
        tarifaRedis.setId(1L);
        tarifaRedis.setValorTarifa(new BigDecimal("100.00"));

        TarifaDTO tarifaDTO = new TarifaDTO(tarifaRedis);

        assertNotNull(tarifaDTO);
        assertEquals(1L, tarifaDTO.getId());
        assertEquals(new BigDecimal("100.00"), tarifaDTO.getValorTarifa());
    }
}
