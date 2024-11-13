package com.parquimetro.domain.dto;

import com.parquimetro.domain.enums.StatusPagamentoEnum;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class RequestVeiculoEstacionadoDTOTest {

    @Test
    void testGettersAndSetters() {
        RequestVeiculoEstacionadoDTO dto = new RequestVeiculoEstacionadoDTO();

        dto.setNumeroProcesso("12345");
        dto.setPlaca("ABC-1234");
        dto.setLocal("Zona A");
        dto.setHorario(LocalDateTime.of(2024, 11, 12, 10, 30));
        dto.setStatusPagamentoEnum(StatusPagamentoEnum.PAGO);

        assertEquals("12345", dto.getNumeroProcesso());
        assertEquals("ABC-1234", dto.getPlaca());
        assertEquals("Zona A", dto.getLocal());
        assertEquals(LocalDateTime.of(2024, 11, 12, 10, 30), dto.getHorario());
        assertEquals(StatusPagamentoEnum.PAGO, dto.getStatusPagamentoEnum());
    }

    @Test
    void testToString() {
        RequestVeiculoEstacionadoDTO dto = RequestVeiculoEstacionadoDTO.builder()
                .numeroProcesso("12345")
                .placa("ABC-1234")
                .local("Zona A")
                .horario(LocalDateTime.of(2024, 11, 12, 10, 30))
                .statusPagamentoEnum(StatusPagamentoEnum.PAGO)
                .build();

        String expectedString = "VeiculoEstacionadoDTO{" +
                ", numeroProcesso='12345'" +
                ", placa='ABC-1234'" +
                ", local='ABC-1234'" +
                ", horario=2024-11-12T10:30" +
                ", statusPagamentoEnum=PAGO" +
                '}';

        assertEquals(expectedString, dto.toString());
    }

    @Test
    void testBuilder() {
        RequestVeiculoEstacionadoDTO dto = RequestVeiculoEstacionadoDTO.builder()
                .numeroProcesso("12345")
                .placa("ABC-1234")
                .local("Zona A")
                .horario(LocalDateTime.of(2024, 11, 12, 10, 30))
                .statusPagamentoEnum(StatusPagamentoEnum.PAGO)
                .build();

        assertNotNull(dto);
        assertEquals("12345", dto.getNumeroProcesso());
        assertEquals("ABC-1234", dto.getPlaca());
        assertEquals("Zona A", dto.getLocal());
        assertEquals(LocalDateTime.of(2024, 11, 12, 10, 30), dto.getHorario());
        assertEquals(StatusPagamentoEnum.PAGO, dto.getStatusPagamentoEnum());
    }

    @Test
    void testEqualsAndHashCode() {
        RequestVeiculoEstacionadoDTO dto1 = RequestVeiculoEstacionadoDTO.builder()
                .numeroProcesso("12345")
                .placa("ABC-1234")
                .local("Zona A")
                .horario(LocalDateTime.of(2024, 11, 12, 10, 30))
                .statusPagamentoEnum(StatusPagamentoEnum.PAGO)
                .build();

        RequestVeiculoEstacionadoDTO dto2 = RequestVeiculoEstacionadoDTO.builder()
                .numeroProcesso("12345")
                .placa("ABC-1234")
                .local("Zona A")
                .horario(LocalDateTime.of(2024, 11, 12, 10, 30))
                .statusPagamentoEnum(StatusPagamentoEnum.PAGO)
                .build();

        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
    }
}
