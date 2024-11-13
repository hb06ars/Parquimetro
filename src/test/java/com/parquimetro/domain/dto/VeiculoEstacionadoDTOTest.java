package com.parquimetro.domain.dto;

import com.parquimetro.domain.entity.VeiculoEstacionado;
import com.parquimetro.domain.enums.StatusPagamentoEnum;
import com.parquimetro.infra.repository.redis.model.VeiculoEstacionadoRedis;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class VeiculoEstacionadoDTOTest {

    @Test
    void testGettersAndSetters() {
        VeiculoEstacionadoDTO veiculoEstacionadoDTO = new VeiculoEstacionadoDTO();
        veiculoEstacionadoDTO.setId("1");
        veiculoEstacionadoDTO.setNumeroProcesso("processo123");
        veiculoEstacionadoDTO.setPlaca("ABC1234");
        veiculoEstacionadoDTO.setLocal("Local 1");
        veiculoEstacionadoDTO.setHoraEntrada(LocalDateTime.now());
        veiculoEstacionadoDTO.setHoraSaida(LocalDateTime.now().plusHours(1));
        veiculoEstacionadoDTO.setValor(new BigDecimal("10.50"));
        veiculoEstacionadoDTO.setStatusPagamentoEnum(StatusPagamentoEnum.PAGO);

        assertEquals("1", veiculoEstacionadoDTO.getId());
        assertEquals("processo123", veiculoEstacionadoDTO.getNumeroProcesso());
        assertEquals("ABC1234", veiculoEstacionadoDTO.getPlaca());
        assertEquals("Local 1", veiculoEstacionadoDTO.getLocal());
        assertNotNull(veiculoEstacionadoDTO.getHoraEntrada());
        assertNotNull(veiculoEstacionadoDTO.getHoraSaida());
        assertEquals(new BigDecimal("10.50"), veiculoEstacionadoDTO.getValor());
        assertEquals(StatusPagamentoEnum.PAGO, veiculoEstacionadoDTO.getStatusPagamentoEnum());
    }

    @Test
    void testBuilder() {
        VeiculoEstacionadoDTO veiculoEstacionadoDTO = VeiculoEstacionadoDTO.builder()
                .id("1")
                .numeroProcesso("processo123")
                .placa("ABC1234")
                .local("Local 1")
                .horaEntrada(LocalDateTime.now())
                .horaSaida(LocalDateTime.now().plusHours(1))
                .valor(new BigDecimal("10.50"))
                .statusPagamentoEnum(StatusPagamentoEnum.PAGO)
                .build();

        assertNotNull(veiculoEstacionadoDTO);
        assertEquals("1", veiculoEstacionadoDTO.getId());
        assertEquals("processo123", veiculoEstacionadoDTO.getNumeroProcesso());
        assertEquals("ABC1234", veiculoEstacionadoDTO.getPlaca());
        assertEquals("Local 1", veiculoEstacionadoDTO.getLocal());
        assertNotNull(veiculoEstacionadoDTO.getHoraEntrada());
        assertNotNull(veiculoEstacionadoDTO.getHoraSaida());
        assertEquals(new BigDecimal("10.50"), veiculoEstacionadoDTO.getValor());
        assertEquals(StatusPagamentoEnum.PAGO, veiculoEstacionadoDTO.getStatusPagamentoEnum());
    }

    @Test
    void testEqualsAndHashCode() {
        VeiculoEstacionadoDTO veiculoEstacionadoDTO1 = VeiculoEstacionadoDTO.builder()
                .id("1")
                .numeroProcesso("processo123")
                .placa("ABC1234")
                .local("Local 1")
                .horaEntrada(LocalDateTime.now())
                .horaSaida(LocalDateTime.now().plusHours(1))
                .valor(new BigDecimal("10.50"))
                .statusPagamentoEnum(StatusPagamentoEnum.PAGO)
                .build();

        VeiculoEstacionadoDTO veiculoEstacionadoDTO2 = VeiculoEstacionadoDTO.builder()
                .id("1")
                .numeroProcesso("processo123")
                .placa("ABC1234")
                .local("Local 1")
                .horaEntrada(LocalDateTime.now())
                .horaSaida(LocalDateTime.now().plusHours(1))
                .valor(new BigDecimal("10.50"))
                .statusPagamentoEnum(StatusPagamentoEnum.PAGO)
                .build();

        assertEquals(veiculoEstacionadoDTO1.getId(), veiculoEstacionadoDTO2.getId());
        assertEquals(veiculoEstacionadoDTO1.hashCode(), veiculoEstacionadoDTO2.hashCode());
    }

    @Test
    void testConstructorFromVeiculoEstacionado() {
        VeiculoEstacionado veiculoEstacionado = new VeiculoEstacionado();
        veiculoEstacionado.setId("1");
        veiculoEstacionado.setNumeroProcesso("processo123");
        veiculoEstacionado.setPlaca("ABC1234");
        veiculoEstacionado.setLocal("Local 1");
        veiculoEstacionado.setHoraEntrada(LocalDateTime.now());
        veiculoEstacionado.setHoraSaida(LocalDateTime.now().plusHours(1));
        veiculoEstacionado.setValor(new BigDecimal("10.50"));
        veiculoEstacionado.setStatusPagamentoEnum(StatusPagamentoEnum.PAGO);

        VeiculoEstacionadoDTO veiculoEstacionadoDTO = new VeiculoEstacionadoDTO(veiculoEstacionado);

        assertNotNull(veiculoEstacionadoDTO);
        assertEquals("1", veiculoEstacionadoDTO.getId());
        assertEquals("processo123", veiculoEstacionadoDTO.getNumeroProcesso());
        assertEquals("ABC1234", veiculoEstacionadoDTO.getPlaca());
        assertEquals("Local 1", veiculoEstacionadoDTO.getLocal());
        assertNotNull(veiculoEstacionadoDTO.getHoraEntrada());
        assertNotNull(veiculoEstacionadoDTO.getHoraSaida());
        assertEquals(new BigDecimal("10.50"), veiculoEstacionadoDTO.getValor());
        assertEquals(StatusPagamentoEnum.PAGO, veiculoEstacionadoDTO.getStatusPagamentoEnum());
    }

    @Test
    void testConstructorFromVeiculoEstacionadoRedis() {
        VeiculoEstacionadoRedis veiculoEstacionadoRedis = new VeiculoEstacionadoRedis();
        veiculoEstacionadoRedis.setNumeroProcesso("processo123");
        veiculoEstacionadoRedis.setPlaca("ABC1234");
        veiculoEstacionadoRedis.setLocal("Local 1");
        veiculoEstacionadoRedis.setHoraEntrada(LocalDateTime.now());
        veiculoEstacionadoRedis.setHoraSaida(LocalDateTime.now().plusHours(1));
        veiculoEstacionadoRedis.setValor(new BigDecimal("10.50"));
        veiculoEstacionadoRedis.setStatusPagamentoEnum(StatusPagamentoEnum.PAGO);

        VeiculoEstacionadoDTO veiculoEstacionadoDTO = new VeiculoEstacionadoDTO(veiculoEstacionadoRedis);

        assertNotNull(veiculoEstacionadoDTO);
        assertEquals("processo123", veiculoEstacionadoDTO.getNumeroProcesso());
        assertEquals("ABC1234", veiculoEstacionadoDTO.getPlaca());
        assertEquals("Local 1", veiculoEstacionadoDTO.getLocal());
        assertNotNull(veiculoEstacionadoDTO.getHoraEntrada());
        assertNotNull(veiculoEstacionadoDTO.getHoraSaida());
        assertEquals(new BigDecimal("10.50"), veiculoEstacionadoDTO.getValor());
        assertEquals(StatusPagamentoEnum.PAGO, veiculoEstacionadoDTO.getStatusPagamentoEnum());
    }
}
