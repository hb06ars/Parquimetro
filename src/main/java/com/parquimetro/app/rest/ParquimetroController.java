package com.parquimetro.app.rest;

import com.parquimetro.app.service.mongo.VeiculoEstacionadoService;
import com.parquimetro.domain.dto.VeiculoEstacionadoDTO;
import com.parquimetro.domain.entity.VeiculoEstacionado;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/parquimetro")
@Slf4j
public class ParquimetroController {

    @Autowired
    private VeiculoEstacionadoService service;

    @GetMapping("/start")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> start() {
        return ResponseEntity.ok("Iniciado.");
    }

    @GetMapping("/{id}")
    public ResponseEntity<VeiculoEstacionadoDTO> findById(@PathVariable Long id) {
        VeiculoEstacionado obj = service.findById(id);
        return ResponseEntity.ok().body(new VeiculoEstacionadoDTO(obj));
    }

    @GetMapping
    public ResponseEntity<List<VeiculoEstacionadoDTO>> findAll() {
        List<VeiculoEstacionado> list = service.findAll();
        List<VeiculoEstacionadoDTO> listDTO = list.stream().map(VeiculoEstacionadoDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok().body(listDTO);
    }

    @PostMapping
    public ResponseEntity<VeiculoEstacionadoDTO> save(@Valid @RequestBody VeiculoEstacionadoDTO dto) {
        VeiculoEstacionado newObj = service.save(new VeiculoEstacionado(dto));
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newObj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<VeiculoEstacionadoDTO> update(@PathVariable Long id, @Valid @RequestBody VeiculoEstacionadoDTO dto) {
        VeiculoEstacionado newObj = service.update(id, new VeiculoEstacionado(dto));
        return ResponseEntity.ok().body(new VeiculoEstacionadoDTO(newObj));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<VeiculoEstacionadoDTO> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }


    /*
    @Cacheable(value = "veiculos", key = "#veiculoId")
        public Veiculo findVeiculoById(String veiculoId) {
            return veiculoRepository.findById(veiculoId).orElse(null);
        }
    */
}