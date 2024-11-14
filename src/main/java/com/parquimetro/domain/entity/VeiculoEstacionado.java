package com.parquimetro.domain.entity;

import com.parquimetro.domain.dto.VeiculoEstacionadoDTO;
import com.parquimetro.domain.enums.StatusPagamentoEnum;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
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
    private String id;

    @Indexed(unique = true)
    private String numeroProcesso;

    @NotBlank(message = "O nome não pode ser vazio ou nulo")
    @Indexed
    private String placa;

    @Indexed
    private LocalDateTime horaEntrada = LocalDateTime.now();

    @Indexed
    private LocalDateTime horaSaida;

    @Indexed
    private StatusPagamentoEnum statusPagamentoEnum = StatusPagamentoEnum.PENDENTE_PAGAMENTO;

    private String local;
    private BigDecimal valor = BigDecimal.ZERO;

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
