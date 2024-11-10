package com.parquimetro.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.parquimetro.infra.repository.redis.model.TarifaRedis;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TarifaDTO implements Serializable {

    private Long id;

    @NotBlank(message = "O nome não pode ser vazio ou nulo")
    private BigDecimal valorTarifa = BigDecimal.ZERO;

    public TarifaDTO(TarifaRedis tarifaRedis) {
        this.id = tarifaRedis.getId();
        this.valorTarifa = tarifaRedis.getValorTarifa();
    }

}