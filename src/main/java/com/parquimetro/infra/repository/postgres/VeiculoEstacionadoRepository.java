package com.parquimetro.infra.repository.postgres;

import com.parquimetro.domain.entity.VeiculoEstacionado;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface VeiculoEstacionadoRepository extends MongoRepository<VeiculoEstacionado, String> {

    Optional<VeiculoEstacionado> findByNumeroProcesso(String numeroProcesso);
    boolean existsByNumeroProcesso(String numeroProcesso);
    void deleteByNumeroProcesso(String numeroProcesso);

    @Query("{'placa': ?0, 'statusPagamentoEnum': ?#{T(com.parquimetro.domain.enums.StatusPagamentoEnum).PENDENTE_PAGAMENTO}}")
    Optional<VeiculoEstacionado> findByPlacaPendentePagamento(String placa);
}