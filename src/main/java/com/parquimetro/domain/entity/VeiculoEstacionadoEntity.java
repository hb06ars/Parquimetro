package com.parquimetro.domain.entity;
import com.parquimetro.domain.dto.VeiculoEstacionadoDTO;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "veiculoEstacionado")
public class VeiculoEstacionadoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String placa;
    private String modelo;

    public VeiculoEstacionadoEntity(VeiculoEstacionadoDTO dto) {
        this.id = dto.getId();
        this.placa = dto.getPlaca();
        this.modelo = dto.getModelo();
    }

    @Override
    public String toString() {
        return "VeiculoEstacionadoEntity{" +
                "id=" + id +
                ", placa='" + placa + '\'' +
                ", modelo='" + modelo + '\'' +
                '}';
    }
}
