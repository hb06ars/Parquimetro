package com.parquimetro.infra.repository.redis.model;

import com.parquimetro.domain.dto.VeiculoEstacionadoDTO;
import com.parquimetro.domain.entity.VeiculoEstacionado;
import com.parquimetro.domain.enums.StatusPagamentoEnum;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@RedisHash("VEICULO_ESTACIONADO")
public class VeiculoEstacionadoRedis {

    @Id
    private String id;
    private String placa;
    private String local;
    private LocalDateTime horaEntrada = LocalDateTime.now();
    private LocalDateTime horaSaida;
    private BigDecimal valor = BigDecimal.ZERO;
    private StatusPagamentoEnum statusPagamentoEnum = StatusPagamentoEnum.PENDENTE_PAGAMENTO;

    public VeiculoEstacionadoRedis(VeiculoEstacionadoDTO dto) {
        this.id = dto.getId();
        this.placa = dto.getPlaca();
        this.local = dto.getLocal();
        this.horaEntrada = dto.getHoraEntrada();
        this.horaSaida = dto.getHoraSaida();
        this.valor = dto.getValor();
        this.statusPagamentoEnum = dto.getStatusPagamentoEnum();
    }

    public VeiculoEstacionadoRedis(VeiculoEstacionado entity) {
        this.id = entity.getId();
        this.placa = entity.getPlaca();
        this.local = entity.getLocal();
        this.horaEntrada = entity.getHoraEntrada();
        this.horaSaida = entity.getHoraSaida();
        this.valor = entity.getValor();
        this.statusPagamentoEnum = entity.getStatusPagamentoEnum();
    }
}
