package com.parquimetro.app.service.postgres;

import com.parquimetro.domain.entity.VeiculoEstacionadoEntity;
import com.parquimetro.infra.exceptions.ObjectNotFoundException;
import com.parquimetro.infra.repository.postgres.VeiculoEstacionadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VeiculoEstacionadoService {

    private final VeiculoEstacionadoRepository repository;

    @Autowired
    public VeiculoEstacionadoService(VeiculoEstacionadoRepository veiculoEstacionadoRepository) {
        this.repository = veiculoEstacionadoRepository;
    }

    public VeiculoEstacionadoEntity save(VeiculoEstacionadoEntity veiculo) {
        return repository.save(veiculo);
    }

    public List<VeiculoEstacionadoEntity> findAll() {
        return repository.findAll();
    }

    public VeiculoEstacionadoEntity findById(Long id) {
        Optional<VeiculoEstacionadoEntity> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! ID: " + id));
    }

    public VeiculoEstacionadoEntity update(Long id, VeiculoEstacionadoEntity veiculoAtualizado) {
        Optional<VeiculoEstacionadoEntity> veiculoExistente = repository.findById(id);

        if (veiculoExistente.isPresent()) {
            VeiculoEstacionadoEntity veiculo = veiculoExistente.get();
            veiculo.setPlaca(veiculoAtualizado.getPlaca());
            veiculo.setModelo(veiculoAtualizado.getModelo());
            return repository.save(veiculo);
        } else {
            throw new RuntimeException("Veículo com ID " + id + " não encontrado.");
        }
    }

    public void delete(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
        } else {
            throw new RuntimeException("Veículo com ID " + id + " não encontrado.");
        }
    }
}
