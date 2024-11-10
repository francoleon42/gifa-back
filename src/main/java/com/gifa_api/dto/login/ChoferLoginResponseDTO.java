package com.gifa_api.dto.login;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class ChoferLoginResponseDTO extends RolEntityDTO {
    private String nombre;
    private Integer numeroTarjeta;
    private Integer tarjetaId;
}
