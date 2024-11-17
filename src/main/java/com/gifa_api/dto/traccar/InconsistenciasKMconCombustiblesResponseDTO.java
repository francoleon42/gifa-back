package com.gifa_api.dto.traccar;

import com.gifa_api.dto.vehiculo.VehiculoResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InconsistenciasKMconCombustiblesResponseDTO {

    List<String> nombresDeResponsables;
    VehiculoResponseDTO vehiculo;
    double kilometrajeRecorrido;
    Double litrosCargados;
    Double litrosInconsistente;

}
