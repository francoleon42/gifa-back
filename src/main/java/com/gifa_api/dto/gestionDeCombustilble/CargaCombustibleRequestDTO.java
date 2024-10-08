package com.gifa_api.dto.gestionDeCombustilble;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class CargaCombustibleRequestDTO {
     Integer cantidadLitros;
     LocalDateTime FechaYhora;
     Integer numeroTarjeta;
}
