package com.parquimetro.infra.repository.redis.impl;

import com.parquimetro.infra.repository.redis.VeiculoEstacionadoRedisRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public class VeiculoEstacionadoRedisRepositoryImpl implements VeiculoEstacionadoRedisRepositoryCustom {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;


    @Override
    public boolean encontrarPlacaNaoPaga(String placa) {
        Set<String> keys = redisTemplate.keys("VEICULO_ESTACIONADO:*");
        return keys != null && !keys.isEmpty();
    }

    @Override
    public String buscarIdPelaPlaca(String placa) {
        Set<String> chaves = redisTemplate.keys("VEICULO_ESTACIONADO:*");

        assert chaves != null;
        for (String chave : chaves) {
            return chave.split(":")[1];
        }
        return null;
    }
}
