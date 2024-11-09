package com.parquimetro.app.rest;

import com.parquimetro.app.service.mongo.VeiculoEstacionadoService;
import com.parquimetro.app.service.redis.TarifaRedisService;
import com.parquimetro.domain.dto.TarifaDTO;
import com.parquimetro.infra.exceptions.ObjectNotFoundException;
import com.parquimetro.infra.repository.redis.model.TarifaRedis;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/parquimetro")
@Slf4j
public class TarifaController {

    @Autowired
    private VeiculoEstacionadoService service;

    @Autowired
    private TarifaRedisService tarifaRedisService;

    @PostMapping("/tarifa")
    public ResponseEntity<TarifaDTO> tarifa(@RequestParam(required = true) BigDecimal valorTarifa) {
        return ResponseEntity.ok(new TarifaDTO(tarifaRedisService
                .save(TarifaDTO.builder()
                        .id(1L)
                        .valorTarifa(valorTarifa)
                        .build())));
    }

    @GetMapping("/tarifa")
    public ResponseEntity<TarifaDTO> buscarTarifa() {
        TarifaRedis cachedItem = tarifaRedisService.findFirstTarifa();
        if (cachedItem != null)
            return ResponseEntity.ok(new TarifaDTO(cachedItem));
        throw new ObjectNotFoundException("Nenhuma tarifa encontrada no sistema.");
    }

}