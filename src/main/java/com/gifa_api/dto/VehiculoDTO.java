package com.gifa_api.dto;

import com.gifa_api.enums.EstadoDeHabilitacion;
import com.gifa_api.enums.EstadoVehiculo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VehiculoDTO {
    private Integer id;
    private String patente;
    private String chasis;
    private Integer antiguedad;
    private Integer kilometraje;
    private Integer litrosDeTanque;
    private String modelo;
    private Integer choferId;
}
