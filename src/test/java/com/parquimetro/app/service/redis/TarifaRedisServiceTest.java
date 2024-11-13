package com.parquimetro.app.service.redis;

import com.parquimetro.domain.dto.TarifaDTO;
import com.parquimetro.infra.repository.redis.TarifaRedisRepository;
import com.parquimetro.infra.repository.redis.model.TarifaRedis;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TarifaRedisServiceTest {

    @Mock
    private TarifaRedisRepository repository;

    @InjectMocks
    private TarifaRedisService tarifaRedisService;

    private TarifaDTO tarifaDTO;
    private TarifaRedis tarifaRedis;
    private Long id = 1L;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        tarifaDTO = new TarifaDTO();
        tarifaDTO.setId(id);
        tarifaDTO.setValorTarifa(BigDecimal.valueOf(15.0));

        tarifaRedis = new TarifaRedis(tarifaDTO);
        tarifaRedis.setId(id);
    }

    @Test
    void testSave() {
        when(repository.save(any(TarifaRedis.class))).thenReturn(tarifaRedis);

        TarifaRedis savedTarifa = tarifaRedisService.save(tarifaDTO);

        assertNotNull(savedTarifa);
        assertEquals(tarifaDTO.getValorTarifa(), savedTarifa.getValorTarifa());
        verify(repository, times(1)).save(any(TarifaRedis.class));
    }

    @Test
    void testFindByIdFound() {
        when(repository.findById(id)).thenReturn(Optional.of(tarifaRedis));

        TarifaRedis foundTarifa = tarifaRedisService.findById(id);

        assertNotNull(foundTarifa);
        assertEquals(id, foundTarifa.getId());
        verify(repository, times(1)).findById(id);
    }

    @Test
    void testFindByIdNotFound() {
        when(repository.findById(id)).thenReturn(Optional.empty());

        TarifaRedis foundTarifa = tarifaRedisService.findById(id);

        assertNull(foundTarifa);
        verify(repository, times(1)).findById(id);
    }

    @Test
    void testFindFirstTarifaFound() {
        when(repository.findAll()).thenReturn(List.of(tarifaRedis));

        TarifaRedis foundTarifa = tarifaRedisService.findFirstTarifa();

        assertNotNull(foundTarifa);
        assertEquals(id, foundTarifa.getId());
        verify(repository, times(1)).findAll();
    }

    @Test
    void testFindFirstTarifaNotFound() {
        when(repository.findAll()).thenReturn(List.of());

        TarifaRedis foundTarifa = tarifaRedisService.findFirstTarifa();

        assertNull(foundTarifa);
        verify(repository, times(1)).findAll();
    }

    @Test
    void testUpdateSuccess() {
        TarifaDTO updatedDTO = new TarifaDTO();
        updatedDTO.setId(1L);
        updatedDTO.setValorTarifa(BigDecimal.valueOf(20.0));

        TarifaRedis updatedTarifaRedis = new TarifaRedis(updatedDTO);
        updatedTarifaRedis.setId(id);

        when(repository.findById(id)).thenReturn(Optional.of(tarifaRedis));
        when(repository.save(any(TarifaRedis.class))).thenReturn(updatedTarifaRedis);

        TarifaRedis updatedTarifa = tarifaRedisService.update(id, updatedDTO);

        assertNotNull(updatedTarifa);
        assertEquals(updatedDTO.getValorTarifa(), updatedTarifa.getValorTarifa());
        verify(repository, times(1)).findById(id);
        verify(repository, times(1)).save(any(TarifaRedis.class));
    }

    @Test
    void testUpdateNotFound() {
        TarifaDTO updatedDTO = new TarifaDTO();
        updatedDTO.setId(id);
        updatedDTO.setValorTarifa(BigDecimal.valueOf(20.0));

        when(repository.findById(id)).thenReturn(Optional.empty());

        TarifaRedis updatedTarifa = tarifaRedisService.update(id, updatedDTO);

        assertNull(updatedTarifa);
        verify(repository, times(1)).findById(id);
    }
}
