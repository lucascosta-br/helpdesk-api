package com.lucascostabr.enums;

public enum TipoPerfil {
    ADMIN("admin"),
    CLIENTE("cliente"),
    TECNICO("técnico");

    private String perfil;

    TipoPerfil(String perfil) {
        this.perfil = perfil;
    }

    public String getPerfil() {
        return perfil;
    }
}
