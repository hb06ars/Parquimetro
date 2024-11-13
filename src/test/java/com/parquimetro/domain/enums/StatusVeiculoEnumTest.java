package com.parquimetro.domain.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StatusVeiculoEnumTest {

    @Test
    void testToEnum() {
        assertEquals(StatusVeiculoEnum.ENTREGUE, StatusVeiculoEnum.toEnum(0));
        assertEquals(StatusVeiculoEnum.DEVOLVIDO, StatusVeiculoEnum.toEnum(1));
    }

    @Test
    void testToEnumInvalidCode() {
        assertThrows(IllegalArgumentException.class, () -> StatusVeiculoEnum.toEnum(99));
    }

    @Test
    void testGetCodigo() {
        assertEquals(0, StatusVeiculoEnum.ENTREGUE.getCodigo());
        assertEquals(1, StatusVeiculoEnum.DEVOLVIDO.getCodigo());
    }

    @Test
    void testGetDescricao() {
        assertEquals("Veículo no estacionamento", StatusVeiculoEnum.ENTREGUE.getDescricao());
        assertEquals("Veículo já retirado do Estacionamento", StatusVeiculoEnum.DEVOLVIDO.getDescricao());
    }
}
