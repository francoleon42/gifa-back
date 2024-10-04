package com.gifa_api.dto.mantenimiento;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OperadorMantenimientoResponseDTO {
    private Integer id;
    private String usuario;
    private String rol;
}
