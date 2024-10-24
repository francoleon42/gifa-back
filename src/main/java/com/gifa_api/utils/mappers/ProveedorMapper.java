package com.gifa_api.utils.mappers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.gifa_api.dto.proveedoresYPedidos.ProveedorResponseDTO;
import com.gifa_api.model.Proveedor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProveedorMapper {

    private final ObjectMapper objectMapper;

    @Autowired
    public ProveedorMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    // Mapea una lista de Proveedores a ProveedorResponseDTO
    public List<ProveedorResponseDTO> mapToProveedorResponseDTO(List<Proveedor> proveedores) {
        return proveedores.stream()
                .map(this::mapToProveedorResponseDTO)
                .collect(Collectors.toList());
    }

    // Mapea un Proveedor a ProveedorResponseDTO
    public ProveedorResponseDTO mapToProveedorResponseDTO(Proveedor proveedor) {
        return ProveedorResponseDTO.builder()
                .email(proveedor.getEmail())
                .nombre(proveedor.getNombre())
                .build();
    }

    // Mapea un ProveedorResponseDTO a Proveedor
    public Proveedor mapToProveedor(ProveedorResponseDTO proveedorDTO) {
        return Proveedor.builder()
                .email(proveedorDTO.getEmail())
                .nombre(proveedorDTO.getNombre())
                .build();
    }
}
