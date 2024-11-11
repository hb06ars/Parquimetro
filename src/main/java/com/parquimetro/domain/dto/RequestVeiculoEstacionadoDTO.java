package com.parquimetro.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.parquimetro.domain.enums.StatusPagamentoEnum;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RequestVeiculoEstacionadoDTO implements Serializable {

    private String numeroProcesso;
    private String placa;
    private String local;
    private LocalDateTime horario;
    private StatusPagamentoEnum statusPagamentoEnum;

    @Override
    public String toString() {
        return "VeiculoEstacionadoDTO{" +
                ", numeroProcesso='" + numeroProcesso + '\'' +
                ", placa='" + placa + '\'' +
                ", local='" + placa + '\'' +
                ", horario=" + horario +
                ", statusPagamentoEnum=" + statusPagamentoEnum +
                '}';
    }

}