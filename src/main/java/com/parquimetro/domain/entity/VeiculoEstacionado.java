package com.parquimetro.domain.entity;
import com.parquimetro.domain.dto.VeiculoEstacionadoDTO;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Document(collection = VeiculoEstacionado.COLLECTION_NAME)
public class VeiculoEstacionado {
    public static final String COLLECTION_NAME = "veiculoEstacionado";

    @Id
    @Indexed
    private String id;
    private String placa;
    private String modelo;

    public VeiculoEstacionado(VeiculoEstacionadoDTO dto) {
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
