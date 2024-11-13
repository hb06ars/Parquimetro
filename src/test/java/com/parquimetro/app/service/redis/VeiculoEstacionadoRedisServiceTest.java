package com.parquimetro.app.service.redis;

import com.parquimetro.domain.dto.VeiculoEstacionadoDTO;
import com.parquimetro.domain.entity.VeiculoEstacionado;
import com.parquimetro.infra.repository.redis.VeiculoEstacionadoRedisRepository;
import com.parquimetro.infra.repository.redis.model.VeiculoEstacionadoRedis;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class VeiculoEstacionadoRedisServiceTest {

    @Mock
    private VeiculoEstacionadoRedisRepository repository;

    @InjectMocks
    private VeiculoEstacionadoRedisService veiculoEstacionadoRedisService;

    private VeiculoEstacionadoDTO veiculoDTO;
    private VeiculoEstacionadoRedis veiculoRedis;
    private String numeroProcesso = "12345";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        veiculoDTO = new VeiculoEstacionadoDTO();
        veiculoDTO.setNumeroProcesso(numeroProcesso);
        veiculoDTO.setPlaca("ABC1234");
        veiculoDTO.setLocal("Rua XYZ");

        veiculoDTO.setHoraEntrada(LocalDateTime.parse("2024-11-12T10:00"));
        veiculoDTO.setHoraSaida(LocalDateTime.parse("2024-11-12T12:00"));

        veiculoDTO.setValor(BigDecimal.valueOf(20.0));
        veiculoRedis = new VeiculoEstacionadoRedis(veiculoDTO);
        veiculoRedis.setNumeroProcesso(numeroProcesso);
    }

    @Test
    void testSave() {
        when(repository.save(any(VeiculoEstacionadoRedis.class))).thenReturn(veiculoRedis);
        VeiculoEstacionadoRedis savedVeiculo = veiculoEstacionadoRedisService.save(veiculoDTO);
        assertNotNull(savedVeiculo);
        assertEquals(veiculoDTO.getPlaca(), savedVeiculo.getPlaca());
        verify(repository, times(1)).save(any(VeiculoEstacionadoRedis.class));
    }

    @Test
    void testSaveAll() {
        when(repository.saveAll(anyIterable())).thenReturn(List.of(veiculoRedis));
        Iterable<VeiculoEstacionadoRedis> savedVeiculos = veiculoEstacionadoRedisService.saveAll(List.of(veiculoRedis));
        assertNotNull(savedVeiculos);
        assertTrue(savedVeiculos.iterator().hasNext());
        verify(repository, times(1)).saveAll(anyIterable());
    }

    @Test
    void testFindByIdFound() {
        when(repository.findById(numeroProcesso)).thenReturn(Optional.of(veiculoRedis));
        VeiculoEstacionadoRedis foundVeiculo = veiculoEstacionadoRedisService.findById(numeroProcesso);
        assertNotNull(foundVeiculo);
        assertEquals(numeroProcesso, foundVeiculo.getNumeroProcesso());
        verify(repository, times(1)).findById(numeroProcesso);
    }

    @Test
    void testFindByIdNotFound() {
        when(repository.findById(numeroProcesso)).thenReturn(Optional.empty());
        VeiculoEstacionadoRedis foundVeiculo = veiculoEstacionadoRedisService.findById(numeroProcesso);
        assertNull(foundVeiculo);
        verify(repository, times(1)).findById(numeroProcesso);
    }

    @Test
    void testUpdateSuccess() {
        VeiculoEstacionado updatedVeiculo = new VeiculoEstacionado();
        updatedVeiculo.setPlaca("DEF5678");
        updatedVeiculo.setLocal("Rua ABC");
        veiculoDTO.setHoraEntrada(LocalDateTime.parse("2024-11-12T08:00"));
        veiculoDTO.setHoraSaida(LocalDateTime.parse("2024-11-12T18:00"));
        updatedVeiculo.setValor(BigDecimal.valueOf(30.0));

        VeiculoEstacionadoRedis updatedVeiculoRedis = new VeiculoEstacionadoRedis(updatedVeiculo);
        updatedVeiculoRedis.setNumeroProcesso(numeroProcesso);

        when(repository.findById(numeroProcesso)).thenReturn(Optional.of(veiculoRedis));
        when(repository.save(any(VeiculoEstacionadoRedis.class))).thenReturn(updatedVeiculoRedis);

        VeiculoEstacionadoRedis updatedVeiculoResult = veiculoEstacionadoRedisService.update(numeroProcesso, updatedVeiculo);

        assertNotNull(updatedVeiculoResult);
        assertEquals(updatedVeiculo.getPlaca(), updatedVeiculoResult.getPlaca());
        assertEquals(updatedVeiculo.getLocal(), updatedVeiculoResult.getLocal());
        verify(repository, times(1)).findById(numeroProcesso);
        verify(repository, times(1)).save(any(VeiculoEstacionadoRedis.class));
    }

    @Test
    void testUpdateNotFound() {
        VeiculoEstacionado updatedVeiculo = new VeiculoEstacionado();
        updatedVeiculo.setPlaca("DEF5678");
        when(repository.findById(numeroProcesso)).thenReturn(Optional.empty());
        VeiculoEstacionadoRedis updatedVeiculoResult = veiculoEstacionadoRedisService.update(numeroProcesso, updatedVeiculo);
        assertNull(updatedVeiculoResult);
        verify(repository, times(1)).findById(numeroProcesso);
    }

    @Test
    void testEncontrarPlacaNaoPagaFound() {
        when(repository.findAll()).thenReturn(List.of(veiculoRedis));
        boolean placaNaoPaga = veiculoEstacionadoRedisService.encontrarPlacaNaoPaga("ABC1234");
        assertTrue(placaNaoPaga);
        verify(repository, times(1)).findAll();
    }

    @Test
    void testEncontrarPlacaNaoPagaNotFound() {
        when(repository.findAll()).thenReturn(List.of());
        boolean placaNaoPaga = veiculoEstacionadoRedisService.encontrarPlacaNaoPaga("XYZ9876");
        assertFalse(placaNaoPaga);
        verify(repository, times(1)).findAll();
    }

    @Test
    void testExcluir() {
        when(repository.findAll()).thenReturn(List.of(veiculoRedis));
        veiculoEstacionadoRedisService.excluir("ABC1234");
        verify(repository, times(1)).deleteById(numeroProcesso);
    }

    @Test
    void testExcluirNotFound() {
        when(repository.findAll()).thenReturn(List.of());
        veiculoEstacionadoRedisService.excluir("ABC1234");
        verify(repository, times(0)).deleteById(anyString());
    }
}
