package com.gifa_api.enums;

public enum Rol {
    ADMINISTRADOR,
    SUPERVISOR,
    GERENTE,
    OPERADOR;

    public static Rol getRol(String rol) {
        return Rol.valueOf(rol.toUpperCase());
    }
}
