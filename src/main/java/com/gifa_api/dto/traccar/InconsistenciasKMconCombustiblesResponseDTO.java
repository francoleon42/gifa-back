package com.gifa_api.dto.traccar;

import com.gifa_api.dto.vehiculo.VehiculoResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InconsistenciasKMconCombustiblesResponseDTO {

    String nombreChofer;
    VehiculoResponseDTO vehiculo;
    Integer kilometrajeRecorrido;
    Double litrosCargados;
    Double litrosInconsistente;

}
