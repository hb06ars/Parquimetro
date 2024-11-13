package com.parquimetro.domain.dto;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Nested
class MessageErrorTest {

    @Test
    void testGettersAndSetters() {
        MessageError messageError = new MessageError();

        messageError.setErro("Erro crítico");
        messageError.setDetalhe("Falha na conexão com o banco de dados");

        assertEquals("Erro crítico", messageError.getErro());
        assertEquals("Falha na conexão com o banco de dados", messageError.getDetalhe());
    }

    @Test
    void testBuilder() {
        MessageError messageError = MessageError.builder()
                .erro("Erro crítico")
                .detalhe("Falha na conexão com o banco de dados")
                .build();

        assertNotNull(messageError);
        assertEquals("Erro crítico", messageError.getErro());
        assertEquals("Falha na conexão com o banco de dados", messageError.getDetalhe());
    }

    @Test
    void testEqualsAndHashCode() {
        MessageError messageError1 = MessageError.builder()
                .erro("Erro crítico")
                .detalhe("Falha na conexão com o banco de dados")
                .build();

        MessageError messageError2 = MessageError.builder()
                .erro("Erro crítico")
                .detalhe("Falha na conexão com o banco de dados")
                .build();

        assertEquals(messageError1, messageError2);
        assertEquals(messageError1.hashCode(), messageError2.hashCode());
    }

    @Test
    void testToString() {
        MessageError messageError = MessageError.builder()
                .erro("Erro crítico")
                .detalhe("Falha na conexão com o banco de dados")
                .build();

        String erro = "Erro crítico";
        String detalhe = "Falha na conexão com o banco de dados";
        assertEquals(erro, messageError.getErro());
        assertEquals(detalhe, messageError.getDetalhe());
    }
}