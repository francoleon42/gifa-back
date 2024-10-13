package com.gifa_api.dto.login;

import com.gifa_api.utils.enums.EstadoChofer;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class ChoferLoginResponseDTO extends RolEntityDTO {
    private String nombre;
}
