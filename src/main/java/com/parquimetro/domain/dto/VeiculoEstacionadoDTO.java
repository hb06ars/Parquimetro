package com.parquimetro.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.parquimetro.domain.entity.VeiculoEstacionado;
import com.parquimetro.domain.enums.StatusPagamentoEnum;
import com.parquimetro.infra.repository.redis.model.VeiculoEstacionadoRedis;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VeiculoEstacionadoDTO implements Serializable {

    private String id;
    private String numeroProcesso;

    @NotBlank(message = "O nome não pode ser vazio ou nulo")
    private String placa;

    private String local;
    private LocalDateTime horaEntrada = LocalDateTime.now();
    private LocalDateTime horaSaida;
    private BigDecimal valor = BigDecimal.ZERO;
    private StatusPagamentoEnum statusPagamentoEnum = StatusPagamentoEnum.PENDENTE_PAGAMENTO;


    public VeiculoEstacionadoDTO(VeiculoEstacionado veiculoEstacionadoEntity) {
        this.id = veiculoEstacionadoEntity.getId();
        this.numeroProcesso = veiculoEstacionadoEntity.getNumeroProcesso();
        this.placa = veiculoEstacionadoEntity.getPlaca();
        this.local = veiculoEstacionadoEntity.getLocal();
        this.horaEntrada = veiculoEstacionadoEntity.getHoraEntrada();
        this.horaSaida = veiculoEstacionadoEntity.getHoraSaida();
        this.valor = veiculoEstacionadoEntity.getValor();
        this.statusPagamentoEnum = veiculoEstacionadoEntity.getStatusPagamentoEnum();
    }

    public VeiculoEstacionadoDTO(VeiculoEstacionadoRedis veiculoEstacionadoRedis) {
        this.numeroProcesso = veiculoEstacionadoRedis.getNumeroProcesso();
        this.placa = veiculoEstacionadoRedis.getPlaca();
        this.local = veiculoEstacionadoRedis.getLocal();
        this.horaEntrada = veiculoEstacionadoRedis.getHoraEntrada();
        this.horaSaida = veiculoEstacionadoRedis.getHoraSaida();
        this.valor = veiculoEstacionadoRedis.getValor();
        this.statusPagamentoEnum = veiculoEstacionadoRedis.getStatusPagamentoEnum();
    }


    @Override
    public String toString() {
        return "VeiculoEstacionadoDTO{" +
                "id=" + id +
                ", numeroProcesso='" + numeroProcesso + '\'' +
                ", placa='" + placa + '\'' +
                ", local='" + placa + '\'' +
                ", horaEntrada=" + horaEntrada +
                ", horaSaida=" + horaSaida +
                ", valor=" + valor +
                ", statusPagamentoEnum=" + statusPagamentoEnum +
                '}';
    }

}