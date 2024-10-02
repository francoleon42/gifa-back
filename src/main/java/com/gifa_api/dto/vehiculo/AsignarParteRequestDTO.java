package com.gifa_api.dto.vehiculo;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Builder
@Data
public class AsignarParteRequestDTO {
    private int vehiculoId;
    private int parteId;
    private LocalDate fecha;
    private Double kilometros;
}
