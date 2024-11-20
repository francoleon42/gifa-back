package com.gifa_api.dto.gestorOperacional;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GestorOperacionalResponseDTO {
    Double presupuesto;
    int consumoDeLitrosPorKm;
}
