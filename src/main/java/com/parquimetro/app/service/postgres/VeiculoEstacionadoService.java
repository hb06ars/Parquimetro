package com.parquimetro.app.service.postgres;

import com.parquimetro.domain.entity.VeiculoEstacionadoEntity;
import com.parquimetro.infra.repository.postgres.VeiculoEstacionadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class VeiculoEstacionadoService {

    private final VeiculoEstacionadoRepository veiculoEstacionadoRepository;

    @Autowired
    public VeiculoEstacionadoService(VeiculoEstacionadoRepository veiculoEstacionadoRepository) {
        this.veiculoEstacionadoRepository = veiculoEstacionadoRepository;
    }

    public VeiculoEstacionadoEntity saveVeiculo(VeiculoEstacionadoEntity veiculo) {
        return veiculoEstacionadoRepository.save(veiculo);
    }

    public List<VeiculoEstacionadoEntity> findAllVeiculos() {
        return veiculoEstacionadoRepository.findAll();
    }

    public Optional<VeiculoEstacionadoEntity> findVeiculoById(Long id) {
        return veiculoEstacionadoRepository.findById(id);
    }

    public VeiculoEstacionadoEntity updateVeiculo(Long id, VeiculoEstacionadoEntity veiculoAtualizado) {
        Optional<VeiculoEstacionadoEntity> veiculoExistente = veiculoEstacionadoRepository.findById(id);

        if (veiculoExistente.isPresent()) {
            VeiculoEstacionadoEntity veiculo = veiculoExistente.get();
            veiculo.setPlaca(veiculoAtualizado.getPlaca());
            veiculo.setModelo(veiculoAtualizado.getModelo());
            return veiculoEstacionadoRepository.save(veiculo);
        } else {
            throw new RuntimeException("Veículo com ID " + id + " não encontrado.");
        }
    }

    public void deleteVeiculo(Long id) {
        if (veiculoEstacionadoRepository.existsById(id)) {
            veiculoEstacionadoRepository.deleteById(id);
        } else {
            throw new RuntimeException("Veículo com ID " + id + " não encontrado.");
        }
    }
}
