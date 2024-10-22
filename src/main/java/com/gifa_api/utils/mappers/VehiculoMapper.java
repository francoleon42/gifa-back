package com.gifa_api.utils.mappers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gifa_api.dto.vehiculo.ListaVehiculosResponseDTO;
import com.gifa_api.dto.vehiculo.VehiculoResponseDTO;
import com.gifa_api.model.Vehiculo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class VehiculoMapper {
    private final ObjectMapper objectMapper;

    @Autowired
    public VehiculoMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
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
                .kilometraje(vehiculo.getKilometraje())
                .modelo(vehiculo.getModelo())
                .estadoVehiculo(vehiculo.getEstadoVehiculo())
                .estadoDeHabilitacion(vehiculo.getEstadoDeHabilitacion())
                .qr(vehiculo.getQr())
                .fechaVencimiento(vehiculo.getFechaVencimiento())
                .build();
    }

}
