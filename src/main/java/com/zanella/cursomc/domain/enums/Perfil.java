package com.zanella.cursomc.domain.enums;

public enum Perfil {

    ADMIN(1, "ROLE_ADMIN"),
    CLIENTE(2, "ROLE_CLIENTE");

    private int cod;
    private String desc;

    Perfil(int cod, String desc) {
        this.cod = cod;
        this.desc = desc;
    }

    public int getCod() {
        return cod;
    }

    public String getDesc() {
        return desc;
    }

    public static Perfil toEnum(Integer cod) {
        if(cod == null) {
            return null;
        }

        for(Perfil ep : Perfil.values()) {
            if(cod.equals(ep.getCod())) {
                return ep;
            }
        }

        throw new IllegalArgumentException("Id inválido: " + cod);
    }
}
