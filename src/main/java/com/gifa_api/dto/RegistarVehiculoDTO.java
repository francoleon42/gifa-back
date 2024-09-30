package com.gifa_api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegistarVehiculoDTO {
    private Integer id;
    private String patente;
    private Integer antiguedad;
    private Integer kilometraje;
    private Integer litrosDeTanque;
    private String modelo;
    private Integer choferId;
}
