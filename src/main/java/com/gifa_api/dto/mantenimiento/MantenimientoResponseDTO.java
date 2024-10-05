package com.gifa_api.dto.mantenimiento;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MantenimientoResponseDTO {
    private Integer id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate fechaInicio;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate fechaFinalizacion;
    private String asunto;
    private String estadoMantenimiento;
    private OperadorMantenimientoResponseDTO operador;
    private VehiculoMantenimientoResponseDTO vehiculo;
    private List<ItemUtilizadoMantenimientoResponseDTO> itemUtilizado;
}
