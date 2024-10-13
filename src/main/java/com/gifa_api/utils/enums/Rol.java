package com.gifa_api.utils.enums;

public enum Rol {
    ADMINISTRADOR,
    SUPERVISOR,
    GERENTE,
    OPERADOR,
    CHOFER;

    public static Rol getRol(String rol) {
        return Rol.valueOf(rol.toUpperCase());
    }
}