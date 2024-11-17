package com.gifa_api.dto.vehiculo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gifa_api.utils.enums.EstadoDeHabilitacion;
import com.gifa_api.utils.enums.EstadoVehiculo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehiculoResponseDTO {
    private Integer id;
    private String patente;
    private Integer antiguedad;
    private double kilometrajeTotal;
    private String modelo;
    private EstadoVehiculo estadoVehiculo;
    private EstadoDeHabilitacion estadoDeHabilitacion;
    private byte[] qr;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate fechaVencimiento;
}