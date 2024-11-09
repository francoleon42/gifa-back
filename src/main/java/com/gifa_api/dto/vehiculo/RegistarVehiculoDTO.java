package com.gifa_api.dto.vehiculo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegistarVehiculoDTO {
    private String patente;
    private Integer antiguedad;
    private Integer kilometraje;
    private String modelo;
    private LocalDate fechaRevision;
}
