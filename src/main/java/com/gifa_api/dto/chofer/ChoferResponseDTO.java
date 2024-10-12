package com.gifa_api.dto.chofer;

import com.gifa_api.dto.vehiculo.VehiculoResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChoferResponseDTO {

    String nombre;
    Integer id_dvehiculo;
    String estadoChofer;
}
