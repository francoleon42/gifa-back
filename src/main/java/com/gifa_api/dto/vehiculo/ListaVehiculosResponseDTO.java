package com.gifa_api.dto.vehiculo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ListaVehiculosResponseDTO {
    private List<VehiculoResponseDTO> vehiculos;
}
