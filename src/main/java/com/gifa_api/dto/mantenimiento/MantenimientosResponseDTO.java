package com.gifa_api.dto.mantenimiento;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class MantenimientosResponseDTO {
    private List<MantenimientoResponseDTO> mantenimientos;
}
