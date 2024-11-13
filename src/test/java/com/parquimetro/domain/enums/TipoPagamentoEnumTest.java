package com.parquimetro.domain.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TipoPagamentoEnumTest {

    @Test
    void testToEnum() {
        assertEquals(TipoPagamentoEnum.CARTAO, TipoPagamentoEnum.toEnum(0));
        assertEquals(TipoPagamentoEnum.BOLETO, TipoPagamentoEnum.toEnum(1));
        assertEquals(TipoPagamentoEnum.PIX, TipoPagamentoEnum.toEnum(2));
    }

    @Test
    void testToEnumInvalidCode() {
        assertThrows(IllegalArgumentException.class, () -> TipoPagamentoEnum.toEnum(99));
    }

    @Test
    void testGetCodigo() {
        assertEquals(0, TipoPagamentoEnum.CARTAO.getCodigo());
        assertEquals(1, TipoPagamentoEnum.BOLETO.getCodigo());
        assertEquals(2, TipoPagamentoEnum.PIX.getCodigo());
    }

    @Test
    void testGetDescricao() {
        assertEquals("CARTÃO", TipoPagamentoEnum.CARTAO.getDescricao());
        assertEquals("BOLETO", TipoPagamentoEnum.BOLETO.getDescricao());
        assertEquals("PIX", TipoPagamentoEnum.PIX.getDescricao());
    }
}
