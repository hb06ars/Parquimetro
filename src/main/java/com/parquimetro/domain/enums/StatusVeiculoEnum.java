package com.parquimetro.domain.enums;

import lombok.Getter;

@Getter
public enum StatusVeiculoEnum {

    ENTREGUE	(0, "Veículo no estacionamento"),
    DEVOLVIDO	(1, "Veículo já retirado do Estacionamento");

    private final Integer codigo;
    private final String descricao;

    private StatusVeiculoEnum(Integer codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public static StatusVeiculoEnum toEnum(Integer cod) {
        if(cod == null) {
            return null;
        }

        for(StatusVeiculoEnum x : StatusVeiculoEnum.values()) {
            if(cod.equals(x.getCodigo())) {
                return x;
            }
        }

        throw new IllegalArgumentException("Tipo de registro inválido");
    }
}