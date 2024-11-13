package com.parquimetro.domain.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class StatusPagamentoEnumTest {

    @Test
    void testToEnum() {
        assertEquals(StatusPagamentoEnum.PENDENTE_PAGAMENTO, StatusPagamentoEnum.toEnum(0));
        assertEquals(StatusPagamentoEnum.PAGO, StatusPagamentoEnum.toEnum(1));
        assertEquals(StatusPagamentoEnum.CANCELADO, StatusPagamentoEnum.toEnum(2));
        assertEquals(StatusPagamentoEnum.EXPIRADO, StatusPagamentoEnum.toEnum(3));
    }

    @Test
    void testToEnumInvalidCode() {
        assertThrows(IllegalArgumentException.class, () -> StatusPagamentoEnum.toEnum(99));
    }

    @Test
    void testGetCodigo() {
        assertEquals(0, StatusPagamentoEnum.PENDENTE_PAGAMENTO.getCodigo());
        assertEquals(1, StatusPagamentoEnum.PAGO.getCodigo());
        assertEquals(2, StatusPagamentoEnum.CANCELADO.getCodigo());
        assertEquals(3, StatusPagamentoEnum.EXPIRADO.getCodigo());
    }

    @Test
    void testGetDescricao() {
        assertEquals("Pendente para pagamento", StatusPagamentoEnum.PENDENTE_PAGAMENTO.getDescricao());
        assertEquals("Pago", StatusPagamentoEnum.PAGO.getDescricao());
        assertEquals("Cancelado", StatusPagamentoEnum.CANCELADO.getDescricao());
        assertEquals("Expirado", StatusPagamentoEnum.EXPIRADO.getDescricao());
    }
}
