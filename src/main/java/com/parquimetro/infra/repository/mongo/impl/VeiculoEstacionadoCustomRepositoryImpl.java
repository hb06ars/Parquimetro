package com.parquimetro.infra.repository.mongo.impl;

import com.parquimetro.domain.dto.RequestVeiculoEstacionadoDTO;
import com.parquimetro.domain.entity.VeiculoEstacionado;
import com.parquimetro.infra.repository.mongo.VeiculoEstacionadoCustomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

@Repository
public class VeiculoEstacionadoCustomRepositoryImpl implements VeiculoEstacionadoCustomRepository {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public Page<VeiculoEstacionado> findAllByCriteria(RequestVeiculoEstacionadoDTO dto, Pageable pageable) {
        Query query = new Query();

        if (dto.getNumeroProcesso() != null) {
            query.addCriteria(Criteria.where("numeroProcesso").is(dto.getNumeroProcesso()));
        }
        if (dto.getPlaca() != null) {
            query.addCriteria(Criteria.where("placa").is(dto.getPlaca()));
        }
        if (dto.getLocal() != null) {
            query.addCriteria(Criteria.where("local").is(dto.getLocal()));
        }
        if (dto.getStatusPagamentoEnum() != null) {
            query.addCriteria(Criteria.where("statusPagamentoEnum").is(dto.getStatusPagamentoEnum()));
        }
        if (dto.getHorario() != null) {
            Criteria criteriaEntrada = Criteria.where("horaEntrada").lte(dto.getHorario());
            Criteria criteriaSaida = new Criteria().orOperator(
                    Criteria.where("horaSaida").gte(dto.getHorario()),
                    Criteria.where("horaSaida").is(null));
            query.addCriteria(new Criteria().andOperator(criteriaEntrada, criteriaSaida));
        }

        long total = mongoTemplate.count(query, VeiculoEstacionado.class);

        // Aplica a paginação
        query.with(pageable);

        var veiculos = mongoTemplate.find(query, VeiculoEstacionado.class);

        return PageableExecutionUtils.getPage(veiculos, pageable, () -> total);
    }
}
