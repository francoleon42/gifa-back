package com.gifa_api.utils.mappers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gifa_api.dto.mantenimiento.MantenimientoResponseDTO;
import com.gifa_api.dto.mantenimiento.MantenimientosResponseDTO;
import com.gifa_api.dto.vehiculo.ListaVehiculosResponseDTO;
import com.gifa_api.dto.vehiculo.VehiculoResponseConQrDTO;
import com.gifa_api.dto.vehiculo.VehiculoResponseDTO;
import com.gifa_api.model.Mantenimiento;
import com.gifa_api.model.Vehiculo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class VehiculoMapper {
    private final ObjectMapper objectMapper;
    private final MantenimientoMapper mantenimientoMapper;

    @Autowired
    public VehiculoMapper(ObjectMapper objectMapper, MantenimientoMapper mantenimientoMapper) {
        this.objectMapper = objectMapper;
        this.mantenimientoMapper = mantenimientoMapper;
    }

    public ListaVehiculosResponseDTO toListaVehiculosResponseDTO(List<Vehiculo> vehiculos) {
        List<VehiculoResponseDTO> vehiculoDTOs = vehiculos.stream()
                .map(this::toVehiculoResponseDTO)
                .collect(Collectors.toList());

        return ListaVehiculosResponseDTO.builder()
                .vehiculos(vehiculoDTOs)
                .build();
    }

    public VehiculoResponseDTO toVehiculoResponseDTO(Vehiculo vehiculo) {
        return VehiculoResponseDTO.builder()
                .id(vehiculo.getId())
                .patente(vehiculo.getPatente())
                .antiguedad(vehiculo.getAntiguedad())
                .kilometrajeTotal(formatKilometraje(vehiculo.getKilometrajeUsado() + vehiculo.getKilometrajeRecorrido()))
                .modelo(vehiculo.getModelo())
                .estadoVehiculo(vehiculo.getEstadoVehiculo())
                .estadoDeHabilitacion(vehiculo.getEstadoDeHabilitacion())
                .qr(vehiculo.getQr())
                .fechaVencimiento(vehiculo.getFechaVencimiento())
                .build();
    }

    private String formatKilometraje(double kilometraje) {
        int kilometros = (int) kilometraje / 1000; // Divide entre 1000 para obtener los km
        int metros = (int) kilometraje % 1000;    // El resto son los metros
        return String.format("%d,%03d", kilometros, metros); // Formato con ceros iniciales para los metros
    }

}
