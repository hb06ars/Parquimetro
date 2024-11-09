package com.parquimetro.app.service.mongo;

import com.parquimetro.domain.entity.VeiculoEstacionado;
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

    public VeiculoEstacionado save(VeiculoEstacionado veiculo) {
        return repository.save(veiculo);
    }

    public List<VeiculoEstacionado> findAll() {
        return repository.findAll();
    }

    public VeiculoEstacionado findByNumeroProcesso(String numeroProcesso) {
        Optional<VeiculoEstacionado> obj = repository.findByNumeroProcesso(numeroProcesso);
        return obj.orElse(null);
    }

    public VeiculoEstacionado update(String numeroProcesso, VeiculoEstacionado veiculoAtualizado) {
        Optional<VeiculoEstacionado> veiculoExistente = repository.findByNumeroProcesso(numeroProcesso);

        if (veiculoExistente.isPresent()) {
            VeiculoEstacionado veiculo = veiculoExistente.get();
            veiculo.setPlaca(veiculoAtualizado.getPlaca());
            veiculo.setLocal(veiculoAtualizado.getLocal());
            veiculo.setHoraEntrada(veiculoAtualizado.getHoraEntrada());
            veiculo.setHoraSaida(veiculoAtualizado.getHoraSaida());
            veiculo.setValor(veiculoAtualizado.getValor());
            veiculo.setStatusPagamentoEnum(veiculoAtualizado.getStatusPagamentoEnum());

            return repository.save(veiculo);
        } else {
            throw new RuntimeException("Veículo com Número do Processo " + numeroProcesso + " não encontrado.");
        }
    }

    public void delete(String numeroProcesso) {
        if (repository.existsByNumeroProcesso(numeroProcesso)) {
            repository.deleteByNumeroProcesso(numeroProcesso);
        } else {
            throw new RuntimeException("Veículo com Número do Processo " + numeroProcesso + " não encontrado.");
        }
    }

    public VeiculoEstacionado findByPlacaPendentePagamento(String placa) {
        return repository.findByPlacaPendentePagamento(placa).orElseThrow( () ->
            new ObjectNotFoundException("Veículo com Número de Placa " + placa + " pendente não encontrado.")
        );
    }
}
