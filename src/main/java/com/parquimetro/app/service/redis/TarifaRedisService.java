package com.parquimetro.app.service.redis;

import com.parquimetro.domain.dto.TarifaDTO;
import com.parquimetro.infra.exceptions.ObjectNotFoundException;
import com.parquimetro.infra.repository.redis.TarifaRedisRepository;
import com.parquimetro.infra.repository.redis.model.TarifaRedis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.Optional;

@Service
public class TarifaRedisService {

    private final TarifaRedisRepository repository;

    @Autowired
    public TarifaRedisService(TarifaRedisRepository tarifaRedisRepository) {
        this.repository = tarifaRedisRepository;
    }

    public TarifaRedis save(TarifaDTO tarifaDTO) {
        return repository.save(new TarifaRedis(tarifaDTO));
    }

    public TarifaRedis findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public TarifaRedis findFirstTarifa() {
        Iterator<TarifaRedis> itens = repository.findAll().iterator();
        if (itens.hasNext()) {
            return itens.next();
        }
        throw new ObjectNotFoundException("Nenhuma tarifa cadastrada no sistema.");
    }

    public TarifaRedis update(Long id, TarifaDTO dto) {
        Optional<TarifaRedis> tarifaRedis = repository.findById(id);

        if (tarifaRedis.isPresent()) {
            TarifaRedis tarifaAtualizadaRedis = tarifaRedis.get();
            tarifaAtualizadaRedis.setValorTarifa(dto.getValorTarifa());
            return repository.save(tarifaAtualizadaRedis);
        } else {
            return null;
        }
    }
}
