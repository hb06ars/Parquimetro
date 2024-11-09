package com.parquimetro.app.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.parquimetro.app.service.kafka.producer.KafkaProducer;
import com.parquimetro.app.service.mongo.VeiculoEstacionadoService;
import com.parquimetro.app.service.redis.VeiculoEstacionadoRedisService;
import com.parquimetro.domain.dto.VeiculoEstacionadoDTO;
import com.parquimetro.domain.entity.VeiculoEstacionado;
import com.parquimetro.infra.repository.redis.model.VeiculoEstacionadoRedis;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/parquimetro")
@Slf4j
public class ParquimetroController {

    @Autowired
    private VeiculoEstacionadoService service;

    @Autowired
    private VeiculoEstacionadoRedisService redisService;

    @Autowired
    private KafkaProducer producer;

    @Autowired
    private ObjectMapper objectMapper;

    @Value("${spring.kafka.topic}")
    private String topico;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> saveorupdate(@RequestBody VeiculoEstacionadoDTO veiculoEstacionadoDTO) throws IOException {
        String payload = objectMapper.writeValueAsString(veiculoEstacionadoDTO);
        producer.sendMessage(topico, payload);
        return ResponseEntity.ok("Tópico enviado.");
    }

    @GetMapping("/{id}")
    @Cacheable(value = "VEICULO_ESTACIONADO", key = "#id")
    public ResponseEntity<VeiculoEstacionadoRedis> findById(@PathVariable String id) {
        VeiculoEstacionadoRedis cachedItem = redisService.findById(id);
        if (cachedItem == null) {
            VeiculoEstacionado obj = service.findById(id);
            cachedItem = new VeiculoEstacionadoRedis(new VeiculoEstacionadoDTO(obj));
            redisService.save(new VeiculoEstacionadoDTO(cachedItem));
        }
        return ResponseEntity.ok().body(cachedItem);
    }

    @GetMapping
    public ResponseEntity<List<VeiculoEstacionadoDTO>> findAll() {
        List<VeiculoEstacionado> list = service.findAll();
        List<VeiculoEstacionadoDTO> listDTO = list.stream().map(VeiculoEstacionadoDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok().body(listDTO);
    }
}