package com.gifa_api.dto.mantenimiento;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VehiculoMantenimientoResponseDTO {
    private Integer id;
    private String patente;
    private Integer antiguedad;
    private double kilometraje;
    private Integer litrosDeTanque;
    private String modelo;
    private String estadoVehiculo;
    private String estadoDeHabilitacion;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate fechaVencimiento;
}
