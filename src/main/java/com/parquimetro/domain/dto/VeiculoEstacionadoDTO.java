package com.parquimetro.domain.dto;

import com.parquimetro.domain.entity.VeiculoEstacionadoEntity;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class VeiculoEstacionadoDTO {

    private Long id;
    private String placa;
    private String modelo;

    public VeiculoEstacionadoDTO(VeiculoEstacionadoEntity veiculoEstacionadoEntity) {
        this.id = veiculoEstacionadoEntity.getId();
        this.placa = veiculoEstacionadoEntity.getPlaca();
        this.modelo = veiculoEstacionadoEntity.getModelo();
    }


    @Override
    public String toString() {
        return "AeronaveDTO{" +
                "id=" + id +
                ", placa='" + placa + '\'' +
                ", modelo=" + modelo +
                '}';
    }

}