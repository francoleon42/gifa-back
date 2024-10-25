package com.gifa_api.dto.login;

import com.gifa_api.utils.enums.Rol;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateResponseDTO {
    private String username;
    private Rol role;
    private RolEntityDTO roleEntity;
}
