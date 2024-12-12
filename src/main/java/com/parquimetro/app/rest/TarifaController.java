package com.parquimetro.app.rest;

import com.parquimetro.app.service.redis.TarifaRedisService;
import com.parquimetro.domain.dto.TarifaDTO;
import com.parquimetro.domain.util.HttpStatusCodes;
import com.parquimetro.infra.exceptions.ObjectNotFoundException;
import com.parquimetro.infra.repository.redis.model.TarifaRedis;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;

@RestController
@RequestMapping("/parquimetro")
@Slf4j
public class TarifaController {

    @Autowired
    private TarifaRedisService tarifaRedisService;

    @Operation(summary = "Cria ou atualiza o valor da tarifa no Cache.",
            description = "Cria/Atualiza e retorna os detalhes da Tarifa do Cache com ID sempre sendo valor 1L.")
    @ApiResponse(responseCode = HttpStatusCodes.OK, description = "Tarifa salva/atualizada")
    @PostMapping("/tarifa")
    public ResponseEntity<@Valid TarifaDTO> tarifa(
            @Parameter(description = "Bigdecimal com valor da tarifa.")
            @RequestParam(required = true) BigDecimal valorTarifa) {
        return ResponseEntity.ok(new TarifaDTO(tarifaRedisService
                .save(TarifaDTO.builder()
                        .id(1L)
                        .valorTarifa(valorTarifa)
                        .build())));
    }

    @Operation(summary = "Busca o valor da tarifa pelo Cache.",
            description = "Retorna os detalhes da Tarifa no Cache.")
    @ApiResponse(responseCode = HttpStatusCodes.OK, description = "Tarifa encontrada no Cache.")
    @ApiResponse(responseCode = HttpStatusCodes.NOT_FOUND, description = "Nenhuma tarifa encontrada no Cache.")
    @GetMapping("/tarifa")
    public ResponseEntity<TarifaDTO> buscarTarifa() {
        TarifaRedis cachedItem = tarifaRedisService.findFirstTarifa();
        if (cachedItem != null)
            return ResponseEntity.ok(new TarifaDTO(cachedItem));
        throw new ObjectNotFoundException("Nenhuma tarifa encontrada no sistema.");
    }

}