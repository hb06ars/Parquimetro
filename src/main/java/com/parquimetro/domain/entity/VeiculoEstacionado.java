package com.parquimetro.domain.entity;
import com.parquimetro.domain.dto.VeiculoEstacionadoDTO;
import com.parquimetro.domain.enums.StatusPagamentoEnum;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "veiculoEstacionado")
public class VeiculoEstacionado  implements Serializable {

    @Id
    @Indexed
    private String id;
    @Indexed
    private String numeroProcesso;

    private String placa;
    private String local;
    private LocalDateTime horaEntrada = LocalDateTime.now();
    private LocalDateTime horaSaida;
    private BigDecimal valor = BigDecimal.ZERO;;
    private StatusPagamentoEnum statusPagamentoEnum = StatusPagamentoEnum.PENDENTE_PAGAMENTO;

    public VeiculoEstacionado(VeiculoEstacionadoDTO dto) {
        this.id = dto.getId();
        this.numeroProcesso = dto.getNumeroProcesso();
        this.placa = dto.getPlaca();
        this.local = dto.getLocal();
        this.horaEntrada = dto.getHoraEntrada();
        this.horaSaida = dto.getHoraSaida();
        this.valor = dto.getValor();
        this.statusPagamentoEnum = dto.getStatusPagamentoEnum();
    }

    @Override
    public String toString() {
        return "VeiculoEstacionadoEntity{" +
                "id=" + id +
                ", numeroProcesso='" + numeroProcesso + '\'' +
                ", placa='" + placa + '\'' +
                ", local='" + local + '\'' +
                '}';
    }
}
