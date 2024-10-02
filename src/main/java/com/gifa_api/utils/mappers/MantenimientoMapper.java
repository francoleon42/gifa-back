package com.gifa_api.utils.mappers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gifa_api.dto.mantenimiento.MantenimientoResponseDTO;
import com.gifa_api.dto.mantenimiento.MantenimientosPendientesResponseDTO;
import com.gifa_api.dto.mantenimiento.MantenimientosResponseDTO;
import com.gifa_api.model.Mantenimiento;

import java.util.List;
import java.util.stream.Collectors;

public class MantenimientoMapper {
    private static final ObjectMapper mapper = new ObjectMapper();


    public MantenimientosResponseDTO mapListToMantenimientosDTO(List<Mantenimiento> mantenimientos) {
        return MantenimientosResponseDTO.builder()
                .mantenimientos(mapToMantenimientosDTO(mantenimientos))
                .build();
    }

    private List<MantenimientoResponseDTO> mapToMantenimientosDTO(List<Mantenimiento> mantenimientos) {
        return mantenimientos.stream()
                .map(mantenimiento -> mapper.convertValue(mantenimiento, MantenimientoResponseDTO.class))
                .collect(Collectors.toList());
    }

    public MantenimientosPendientesResponseDTO mapListToMantenimientosPendientesDTO(List<Mantenimiento> mantenimientos) {
        return MantenimientosPendientesResponseDTO.builder()
                .mantenimientos(mapToMantenimientosDTO(mantenimientos))
                .build();
    }
}
