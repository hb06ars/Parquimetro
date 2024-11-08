package com.parquimetro.domain.dto;

import com.parquimetro.domain.entity.VeiculoEstacionado;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class VeiculoEstacionadoDTO {

    private String id;
    private String placa;
    private String modelo;

    public VeiculoEstacionadoDTO(VeiculoEstacionado veiculoEstacionadoEntity) {
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