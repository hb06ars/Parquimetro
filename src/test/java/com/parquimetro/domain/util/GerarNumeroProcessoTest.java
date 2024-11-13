package com.parquimetro.domain.util;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

class GerarNumeroProcessoTest {

    @Test
    void testExecuteWithPlaca() {
        String placa = "ABC1234";
        String numeroProcesso = GerarNumeroProcesso.execute(placa);
        assertNotNull(numeroProcesso);
        assertTrue(numeroProcesso.startsWith(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))));
        assertTrue(numeroProcesso.endsWith(placa));
    }

    @Test
    void testExecuteWithNullPlaca() {
        String numeroProcesso = GerarNumeroProcesso.execute(null);
        assertNotNull(numeroProcesso);
        assertTrue(numeroProcesso.startsWith(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))));
        assertTrue(numeroProcesso.length() > 14);  // Verifica que a parte aleatória foi gerada
    }

    @Test
    void testGerarNumerosAleatorios() {
        String numerosAleatorios = GerarNumeroProcesso.gerarNumerosAleatorios();
        assertNotNull(numerosAleatorios);
        assertEquals(6, numerosAleatorios.length());
        assertTrue(numerosAleatorios.matches("[A-Z]{6}"));
    }

    @Test
    void testGerarNumerosAleatoriosWhenShort() {
        String numerosAleatorios = GerarNumeroProcesso.gerarNumerosAleatorios();
        assertNotNull(numerosAleatorios);
        assertTrue(numerosAleatorios.length() <= 6);
    }
}
