package com.parquimetro.domain.enums;

import lombok.Getter;

@Getter
public enum TipoPagamentoEnum {

    CARTAO	(0, "CARTÃO"),
    BOLETO	(1, "BOLETO"),
    PIX	(2, "PIX");

    private final Integer codigo;
    private final String descricao;

    private TipoPagamentoEnum(Integer codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public static TipoPagamentoEnum toEnum(Integer cod) {
        if(cod == null) {
            return null;
        }

        for(TipoPagamentoEnum x : TipoPagamentoEnum.values()) {
            if(cod.equals(x.getCodigo())) {
                return x;
            }
        }

        throw new IllegalArgumentException("Tipo de Pagamento inválido");
    }
}