package com.gifa_api.utils.mappers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gifa_api.dto.mantenimiento.MantenimientoResponseDTO;
import com.gifa_api.dto.mantenimiento.MantenimientosResponseDTO;
import com.gifa_api.dto.vehiculo.VehiculoResponseConQrDTO;
import com.gifa_api.dto.vehiculo.VehiculoResponseDTO;
import com.gifa_api.model.Mantenimiento;
import com.gifa_api.model.Vehiculo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class VehiculoResponseConQrMapper {
    private final ObjectMapper objectMapper;
    private final MantenimientoMapper mantenimientoMapper;

    @Autowired
    public VehiculoResponseConQrMapper(ObjectMapper objectMapper, MantenimientoMapper mantenimientoMapper) {
        this.objectMapper = objectMapper;
        this.mantenimientoMapper = mantenimientoMapper;
    }

    public VehiculoResponseConQrDTO toVehiculoResponseConQrDTO(Vehiculo vehiculo, List<Mantenimiento> mantenimientos) {
        VehiculoResponseDTO vehiculoDTO = toVehiculoResponseDTO(vehiculo);

        // Mapear la lista de mantenimientos a un MantenimientosResponseDTO
        MantenimientosResponseDTO mantenimientosResponseDTO = MantenimientosResponseDTO.builder()
                .mantenimientos(mapToMantenimientosDTO(mantenimientos))
                .build();

        return VehiculoResponseConQrDTO.builder()
                .vehiculo(vehiculoDTO)
                .mantenimientos(mantenimientosResponseDTO)
                .build();
    }

    private List<MantenimientoResponseDTO> mapToMantenimientosDTO(List<Mantenimiento> mantenimientos) {
        return mantenimientos.stream()
                .map(mantenimientoMapper::mapToMantenimientoResponseDTO)
                .collect(Collectors.toList());
    }

    private VehiculoResponseDTO toVehiculoResponseDTO(Vehiculo vehiculo) {
        return VehiculoResponseDTO.builder()
                .id(vehiculo.getId())
                .patente(vehiculo.getPatente())
                .antiguedad(vehiculo.getAntiguedad())
                .kilometrajeTotal(vehiculo.getKilometrajeUsado() + vehiculo.getKilometrajeRecorrido())
                .modelo(vehiculo.getModelo())
                .estadoVehiculo(vehiculo.getEstadoVehiculo())
                .estadoDeHabilitacion(vehiculo.getEstadoDeHabilitacion())
                .qr(vehiculo.getQr())
                .fechaVencimiento(vehiculo.getFechaVencimiento())
                .build();
    }
}