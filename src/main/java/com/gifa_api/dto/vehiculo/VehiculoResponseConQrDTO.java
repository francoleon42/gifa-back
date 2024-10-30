package com.gifa_api.dto.vehiculo;

import com.gifa_api.dto.chofer.ChoferResponseDTO;
import com.gifa_api.dto.mantenimiento.MantenimientoResponseDTO;
import com.gifa_api.dto.mantenimiento.MantenimientosResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehiculoResponseConQrDTO {
     VehiculoResponseDTO vehiculo;
     MantenimientosResponseDTO mantenimientos;

}
