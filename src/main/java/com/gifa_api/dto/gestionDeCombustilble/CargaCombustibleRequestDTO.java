package com.gifa_api.dto.gestionDeCombustilble;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CargaCombustibleRequestDTO {
     Integer cantidadLitros;
     @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
     LocalDateTime FechaYhora;
     Integer numeroTarjeta;
}
