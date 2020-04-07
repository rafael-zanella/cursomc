package com.zanella.cursomc.domain.enums;

public enum EstadoPagamento {

    PENDENTE(1, "Pendente"),
    QUITADO(2, "Quitado"),
    CANCELADO(3, "Cancelado");

    private int cod;
    private String desc;

    EstadoPagamento(int cod, String desc) {
        this.cod = cod;
        this.desc = desc;
    }

    public int getCod() {
        return cod;
    }

    public String getDesc() {
        return desc;
    }

    public static EstadoPagamento toEnum(Integer cod) {
        if(cod == null) {
            return null;
        }

        for(EstadoPagamento ep : EstadoPagamento.values()) {
            if(cod.equals(ep.getCod())) {
                return ep;
            }
        }

        throw new IllegalArgumentException("Id inválido: " + cod);
    }
}
