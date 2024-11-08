package com.parquimetro.domain.dto;

import com.parquimetro.domain.entity.VeiculoEstacionado;
import com.parquimetro.domain.enums.StatusPagamentoEnum;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class VeiculoEstacionadoDTO {

    private String id;
    private String placa;
    private String local;
    private LocalDateTime horaEntrada = LocalDateTime.now();
    private LocalDateTime horaSaida;
    private BigDecimal valor = BigDecimal.ZERO;
    private StatusPagamentoEnum statusPagamentoEnum = StatusPagamentoEnum.PENDENTE_PAGAMENTO;


    public VeiculoEstacionadoDTO(VeiculoEstacionado veiculoEstacionadoEntity) {
        this.id = veiculoEstacionadoEntity.getId();
        this.placa = veiculoEstacionadoEntity.getPlaca();
        this.local = veiculoEstacionadoEntity.getLocal();
        this.horaEntrada = veiculoEstacionadoEntity.getHoraEntrada();
        this.horaSaida = veiculoEstacionadoEntity.getHoraSaida();
        this.valor = veiculoEstacionadoEntity.getValor();
        this.statusPagamentoEnum = veiculoEstacionadoEntity.getStatusPagamentoEnum();
    }


    @Override
    public String toString() {
        return "VeiculoEstacionadoDTO{" +
                "id=" + id +
                ", placa='" + placa + '\'' +
                ", local='" + placa + '\'' +
                ", horaEntrada=" + horaEntrada +
                ", horaSaida=" + horaSaida +
                ", valor=" + valor +
                ", statusPagamentoEnum=" + statusPagamentoEnum +
                '}';
    }

}