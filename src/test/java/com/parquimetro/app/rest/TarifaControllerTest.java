package com.parquimetro.app.rest;

import com.parquimetro.app.service.mongo.VeiculoEstacionadoService;
import com.parquimetro.app.service.redis.TarifaRedisService;
import com.parquimetro.domain.dto.TarifaDTO;
import com.parquimetro.infra.repository.redis.model.TarifaRedis;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

class TarifaControllerTest {
    @Mock
    VeiculoEstacionadoService service;
    @Mock
    TarifaRedisService tarifaRedisService;
    @InjectMocks
    TarifaController tarifaController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testTarifa() {
        when(tarifaRedisService.save(any(TarifaDTO.class))).thenReturn(new TarifaRedis(0L, new BigDecimal(0)));

        ResponseEntity<TarifaDTO> result = tarifaController.tarifa(new BigDecimal(0));
        Assertions.assertEquals(new ResponseEntity<TarifaDTO>(new TarifaDTO(Long.valueOf(1), new BigDecimal(0)), null, HttpStatus.OK).getStatusCode(), result.getStatusCode());
    }

    @Test
    void testBuscarTarifa() {
        when(tarifaRedisService.findFirstTarifa()).thenReturn(new TarifaRedis(0L, new BigDecimal(0)));

        ResponseEntity<TarifaDTO> result = tarifaController.buscarTarifa();
        Assertions.assertEquals(new ResponseEntity<TarifaDTO>(new TarifaDTO(Long.valueOf(1), new BigDecimal(0)), null, HttpStatus.OK).getStatusCode(), result.getStatusCode());
    }
}

//Generated with love by TestMe :) Please raise issues & feature requests at: https://weirddev.com/forum#!/testme