package com.parquimetro.infra.repository.redis.model;

import com.parquimetro.domain.dto.TarifaDTO;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@RedisHash("TARIFA")
public class TarifaRedis {

    @Id
    private long id = 1;
    private BigDecimal valorTarifa = BigDecimal.ZERO;

    public TarifaRedis(TarifaDTO dto) {
        this.id = dto.getId();
        this.valorTarifa = dto.getValorTarifa();
    }

}
