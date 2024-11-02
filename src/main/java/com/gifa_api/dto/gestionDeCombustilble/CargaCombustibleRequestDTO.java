package com.gifa_api.dto.gestionDeCombustilble;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CargaCombustibleRequestDTO {
     Integer cantidadLitros;
     Integer numeroTarjeta;
}
