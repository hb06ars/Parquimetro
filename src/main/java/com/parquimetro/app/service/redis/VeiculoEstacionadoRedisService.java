package com.parquimetro.app.service.redis;

import com.parquimetro.domain.dto.VeiculoEstacionadoDTO;
import com.parquimetro.domain.entity.VeiculoEstacionado;
import com.parquimetro.infra.repository.redis.VeiculoEstacionadoRedisRepository;
import com.parquimetro.infra.repository.redis.model.VeiculoEstacionadoRedis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
public class VeiculoEstacionadoRedisService {

    private final VeiculoEstacionadoRedisRepository repository;

    @Autowired
    public VeiculoEstacionadoRedisService(VeiculoEstacionadoRedisRepository veiculoEstacionadoRedisRepository) {
        this.repository = veiculoEstacionadoRedisRepository;
    }

    public VeiculoEstacionadoRedis save(VeiculoEstacionadoDTO veiculo) {
        return repository.save(new VeiculoEstacionadoRedis(veiculo));
    }

    public Iterable<VeiculoEstacionadoRedis> saveAll(Iterable<VeiculoEstacionadoRedis> veiculos) {
        return repository.saveAll(veiculos);
    }

    public Iterable<VeiculoEstacionadoRedis> findAll() {
        return repository.findAll();
    }

    public VeiculoEstacionadoRedis findById(String numeroProcesso) {
        return repository.findById(numeroProcesso).orElse(null);
    }

    public VeiculoEstacionadoRedis update(String numeroProcesso, VeiculoEstacionado veiculoAtualizado) {
        Optional<VeiculoEstacionadoRedis> veiculoExistente = repository.findById(numeroProcesso);

        if (veiculoExistente.isPresent()) {
            VeiculoEstacionadoRedis veiculo = veiculoExistente.get();
            veiculo.setPlaca(veiculoAtualizado.getPlaca());
            veiculo.setLocal(veiculoAtualizado.getLocal());
            veiculo.setHoraEntrada(veiculoAtualizado.getHoraEntrada());
            veiculo.setHoraSaida(veiculoAtualizado.getHoraSaida());
            veiculo.setValor(veiculoAtualizado.getValor());
            veiculo.setStatusPagamentoEnum(veiculoAtualizado.getStatusPagamentoEnum());

            return repository.save(veiculo);
        } else {
            return null;
        }
    }

    public boolean encontrarPlacaNaoPaga(String placa) {
        return StreamSupport
                .stream(findAll().spliterator(), false)
                .anyMatch(veiculo -> veiculo.getPlaca().equals(placa));
    }

    public void excluir(String placa) {
        StreamSupport
                .stream(findAll().spliterator(), false)
                .filter(veiculo -> veiculo.getPlaca().equals(placa))
                .findFirst()
                .map(VeiculoEstacionadoRedis::getNumeroProcesso).ifPresent(repository::deleteById);
    }
}
