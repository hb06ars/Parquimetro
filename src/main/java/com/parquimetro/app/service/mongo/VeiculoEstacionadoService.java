package com.parquimetro.app.service.mongo;

import com.parquimetro.domain.dto.RequestVeiculoEstacionadoDTO;
import com.parquimetro.domain.entity.VeiculoEstacionado;
import com.parquimetro.infra.exceptions.ObjectNotFoundException;
import com.parquimetro.infra.repository.mongo.VeiculoEstacionadoCustomRepository;
import com.parquimetro.infra.repository.mongo.VeiculoEstacionadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public VeiculoEstacionado save(VeiculoEstacionado veiculoSalvar) {
        try{
            return repository.save(veiculoSalvar);
        } catch(OptimisticLockingFailureException e){
            var veiculoExistente = repository.findById(veiculoSalvar.getId()).orElse(null);
            if(veiculoExistente != null){
                atualizarPayloadVeiculoEstacionado(veiculoExistente, veiculoSalvar, true);
                return repository.save(veiculoExistente);
            } else{
                throw new RuntimeException("Houve um problema para salvar o veículo, tente novamente!");
            }
        }
    }

    @Transactional(readOnly = true)
    public List<VeiculoEstacionado> findAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public VeiculoEstacionado findByNumeroProcesso(String numeroProcesso) {
        Optional<VeiculoEstacionado> obj = repository.findByNumeroProcesso(numeroProcesso);
        return obj.orElse(null);
    }

    @Transactional
    public VeiculoEstacionado update(String numeroProcesso, VeiculoEstacionado veiculoSalvar) {
        try{
            Optional<VeiculoEstacionado> veiculoExistente = repository.findByNumeroProcesso(numeroProcesso);
            if (veiculoExistente.isPresent()) {
                atualizarPayloadVeiculoEstacionado(veiculoExistente.get(), veiculoSalvar, false);
                return repository.save(veiculoExistente.get());
            } else {
                throw new RuntimeException("Veículo com Número do Processo " + numeroProcesso + " não encontrado.");
            }
        } catch(OptimisticLockingFailureException e){
            var veiculoExistente = repository.findById(veiculoSalvar.getId()).orElse(null);
            if(veiculoExistente != null){
                atualizarPayloadVeiculoEstacionado(veiculoExistente, veiculoSalvar, true);
                return repository.save(veiculoExistente);
            } else{
                throw new RuntimeException("Houve um problema para atualizar o veículo, tente novamente!");
            }
        }
    }

    @Transactional
    public void delete(String numeroProcesso) {
        try{
            if (repository.existsByNumeroProcesso(numeroProcesso)) {
                repository.deleteByNumeroProcesso(numeroProcesso);
            } else {
                throw new RuntimeException("Veículo com Número do Processo " + numeroProcesso + " não encontrado.");
            }
        } catch(OptimisticLockingFailureException e){
            var veiculoExistente = repository.findById(numeroProcesso).orElse(null);
            if(veiculoExistente != null){
                repository.deleteByNumeroProcesso(numeroProcesso);
            } else{
                throw new RuntimeException("O veículo já foi removido!");
            }
        }
    }

    @Transactional(readOnly = true)
    public VeiculoEstacionado findByPlacaPendentePagamento(String placa) {
        return repository.findByPlacaPendentePagamento(placa).orElseThrow( () ->
            new ObjectNotFoundException("Veículo com Número de Placa " + placa + " pendente não encontrado.")
        );
    }

    @Transactional(readOnly = true)
    public Page<VeiculoEstacionado> buscaPaginada(RequestVeiculoEstacionadoDTO dto, int page, int size, String sortField, String sortDirection) {
        Pageable pageable = PageRequest.of(page, size);
        return customRepository.findAllByCriteria(dto, pageable, sortField, sortDirection);
    }

    private static void atualizarPayloadVeiculoEstacionado(VeiculoEstacionado veiculo, VeiculoEstacionado veiculoAtualizado, boolean atualizarVersao) {
        if(atualizarVersao)
            veiculo.setVersion(veiculoAtualizado.getVersion() + 1);
        veiculo.setNumeroProcesso(veiculoAtualizado.getNumeroProcesso());
        veiculo.setPlaca(veiculoAtualizado.getPlaca());
        veiculo.setHoraEntrada(veiculoAtualizado.getHoraEntrada());
        veiculo.setHoraSaida(veiculoAtualizado.getHoraSaida());
        veiculo.setStatusPagamentoEnum(veiculoAtualizado.getStatusPagamentoEnum());
        veiculo.setLocal(veiculoAtualizado.getLocal());
        veiculo.setValor(veiculoAtualizado.getValor());
    }
}
