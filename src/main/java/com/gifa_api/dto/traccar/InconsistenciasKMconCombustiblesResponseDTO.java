package com.gifa_api.dto.traccar;

import com.gifa_api.dto.vehiculo.VehiculoResponseDTO;

public class InconsistenciasKMconCombustiblesResponseDTO {

    String nombreChofer;
    VehiculoResponseDTO vehiculo;
    Integer kilometrajeRecorrido;
    Integer litrosCargados;
    Double litrosInconsistente;

}
