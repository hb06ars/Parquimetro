package com.parquimetro.domain.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AjustesStringTest {

    @Test
    void testRemoverTracosCpf() {
        assertEquals("12345678909", AjustesString.removerTracosCpf("123.456.789-09"));
        assertEquals("12345678909", AjustesString.removerTracosCpf("123/456/789.09"));
        assertEquals("12345678909", AjustesString.removerTracosCpf("12345678909"));
        assertEquals("12345678909", AjustesString.removerTracosCpf(" 123.456.789-09 "));
    }

    @Test
    void testRemoverTracosCpfWithEmptyString() {
        assertEquals("", AjustesString.removerTracosCpf(""));
    }

    @Test
    void testRemoverTracosCpfWithNull() {
        assertThrows(NullPointerException.class, () -> AjustesString.removerTracosCpf(null));
    }
}
