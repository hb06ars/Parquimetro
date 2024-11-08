package com.parquimetro.domain.enums;

import lombok.Getter;

@Getter
public enum StatusPagamentoEnum {

    PENDENTE_PAGAMENTO	(0, "Pendente para pagamento"),
    PAGO	(1, "Pago"),
    CANCELADO	(2, "Cancelado"),
    EXPIRADO	(3, "Expirado");

    private final Integer codigo;
    private final String descricao;

    private StatusPagamentoEnum(Integer codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public static StatusPagamentoEnum toEnum(Integer cod) {
        if(cod == null) {
            return null;
        }

        for(StatusPagamentoEnum x : StatusPagamentoEnum.values()) {
            if(cod.equals(x.getCodigo())) {
                return x;
            }
        }

        throw new IllegalArgumentException("Status de Pagamento inválido");
    }
}