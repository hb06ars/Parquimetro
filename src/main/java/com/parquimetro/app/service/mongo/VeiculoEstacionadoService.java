package com.parquimetro.app.service.mongo;

import com.parquimetro.domain.dto.RequestVeiculoEstacionadoDTO;
import com.parquimetro.domain.entity.VeiculoEstacionado;
import com.parquimetro.infra.exceptions.ObjectNotFoundException;
import com.parquimetro.infra.repository.mongo.VeiculoEstacionadoCustomRepository;
import com.parquimetro.infra.repository.mongo.VeiculoEstacionadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VeiculoEstacionadoService {

    private final VeiculoEstacionadoRepository repository;
    private final VeiculoEstacionadoCustomRepository customRepository;

    @Autowired
    public VeiculoEstacionadoService(VeiculoEstacionadoRepository veiculoEstacionadoRepository, VeiculoEstacionadoCustomRepository customRepository) {
        this.repository = veiculoEstacionadoRepository;
        this.customRepository = customRepository;
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

    public Page<VeiculoEstacionado> buscaPaginada(RequestVeiculoEstacionadoDTO dto, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return customRepository.findAllByCriteria(dto, pageable);
    }
}
