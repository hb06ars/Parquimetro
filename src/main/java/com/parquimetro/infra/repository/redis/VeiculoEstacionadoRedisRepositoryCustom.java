package com.parquimetro.infra.repository.redis;

public interface VeiculoEstacionadoRedisRepositoryCustom {
    boolean encontrarPlacaNaoPaga(String placa);
    String buscarIdPelaPlaca(String placa);
}
