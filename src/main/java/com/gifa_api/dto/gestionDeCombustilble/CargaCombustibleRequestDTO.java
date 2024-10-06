package com.gifa_api.dto.gestionDeCombustilble;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class CargaCombustibleRequestDTO {
    Integer cantidadLitros;
    LocalDate FechaYhora;
    Integer numeroTarjeta;
}
