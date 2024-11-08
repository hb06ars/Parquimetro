package com.parquimetro.app.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.parquimetro.app.service.kafka.producer.KafkaProducer;
import com.parquimetro.app.service.mongo.VeiculoEstacionadoService;
import com.parquimetro.domain.dto.VeiculoEstacionadoDTO;
import com.parquimetro.domain.entity.VeiculoEstacionado;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    private KafkaProducer producer;

    @Autowired
    private ObjectMapper objectMapper;

    @Value("${spring.kafka.topic}")
    private String topico;

    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> saveorupdate(@RequestBody VeiculoEstacionadoDTO veiculoEstacionadoDTO) throws IOException {
        String payload = objectMapper.writeValueAsString(veiculoEstacionadoDTO);
        producer.sendMessage(topico, payload);
        return ResponseEntity.ok("Tópico enviado.");
    }

    @GetMapping("/{id}")
    public ResponseEntity<VeiculoEstacionadoDTO> findById(@PathVariable String id) {
        VeiculoEstacionado obj = service.findById(id);
        return ResponseEntity.ok().body(new VeiculoEstacionadoDTO(obj));
    }

    @GetMapping
    public ResponseEntity<List<VeiculoEstacionadoDTO>> findAll() {
        List<VeiculoEstacionado> list = service.findAll();
        List<VeiculoEstacionadoDTO> listDTO = list.stream().map(VeiculoEstacionadoDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok().body(listDTO);
    }

    /*
    @Cacheable(value = "veiculos", key = "#veiculoId")
        public Veiculo findVeiculoById(String veiculoId) {
            return veiculoRepository.findById(veiculoId).orElse(null);
        }
    */
}