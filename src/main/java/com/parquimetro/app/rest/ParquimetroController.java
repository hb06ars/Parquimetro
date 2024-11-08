package com.parquimetro.app.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/parquimetro")
@Slf4j
public class ParquimetroController {

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> start() {
        return ResponseEntity.ok("Iniciado.");
    }


    /*
    @Cacheable(value = "veiculos", key = "#veiculoId")
        public Veiculo findVeiculoById(String veiculoId) {
            return veiculoRepository.findById(veiculoId).orElse(null);
        }
    */
}