package com.parquimetro.domain.entity;

import com.parquimetro.domain.dto.VeiculoEstacionadoDTO;
import com.parquimetro.domain.enums.StatusPagamentoEnum;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class VeiculoEstacionadoTest {

    @Test
    void testGettersAndSetters() {
        VeiculoEstacionado veiculoEstacionado = new VeiculoEstacionado();
        veiculoEstacionado.setId("1");
        veiculoEstacionado.setNumeroProcesso("processo123");
        veiculoEstacionado.setPlaca("ABC1234");
        veiculoEstacionado.setLocal("Local 1");
        veiculoEstacionado.setHoraEntrada(LocalDateTime.now());
        veiculoEstacionado.setHoraSaida(LocalDateTime.now().plusHours(1));
        veiculoEstacionado.setValor(new BigDecimal("10.50"));
        veiculoEstacionado.setStatusPagamentoEnum(StatusPagamentoEnum.PAGO);

        assertEquals("1", veiculoEstacionado.getId());
        assertEquals("processo123", veiculoEstacionado.getNumeroProcesso());
        assertEquals("ABC1234", veiculoEstacionado.getPlaca());
        assertEquals("Local 1", veiculoEstacionado.getLocal());
        assertNotNull(veiculoEstacionado.getHoraEntrada());
        assertNotNull(veiculoEstacionado.getHoraSaida());
        assertEquals(new BigDecimal("10.50"), veiculoEstacionado.getValor());
        assertEquals(StatusPagamentoEnum.PAGO, veiculoEstacionado.getStatusPagamentoEnum());
    }

    @Test
    void testConstructorFromVeiculoEstacionadoDTO() {
        VeiculoEstacionadoDTO dto = VeiculoEstacionadoDTO.builder()
                .id("1")
                .numeroProcesso("processo123")
                .placa("ABC1234")
                .local("Local 1")
                .horaEntrada(LocalDateTime.now())
                .horaSaida(LocalDateTime.now().plusHours(1))
                .valor(new BigDecimal("10.50"))
                .statusPagamentoEnum(StatusPagamentoEnum.PAGO)
                .build();

        VeiculoEstacionado veiculoEstacionado = new VeiculoEstacionado(dto);

        assertEquals("1", veiculoEstacionado.getId());
        assertEquals("processo123", veiculoEstacionado.getNumeroProcesso());
        assertEquals("ABC1234", veiculoEstacionado.getPlaca());
        assertEquals("Local 1", veiculoEstacionado.getLocal());
        assertNotNull(veiculoEstacionado.getHoraEntrada());
        assertNotNull(veiculoEstacionado.getHoraSaida());
        assertEquals(new BigDecimal("10.50"), veiculoEstacionado.getValor());
        assertEquals(StatusPagamentoEnum.PAGO, veiculoEstacionado.getStatusPagamentoEnum());
    }

    @Test
    void testToString() {
        VeiculoEstacionado veiculoEstacionado = new VeiculoEstacionado();
        veiculoEstacionado.setId("1");
        veiculoEstacionado.setNumeroProcesso("processo123");
        veiculoEstacionado.setPlaca("ABC1234");
        veiculoEstacionado.setLocal("Local 1");

        String expected = "VeiculoEstacionadoEntity{id=1, numeroProcesso='processo123', placa='ABC1234', local='Local 1'}";
        assertEquals(expected, veiculoEstacionado.toString());
    }
}
