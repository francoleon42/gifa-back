package com.gifa_api.utils.mappers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gifa_api.dto.proveedoresYPedidos.ProveedorDeITemResponseDTO;
import com.gifa_api.model.ProveedorDeItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProveedorDeItemMapper {

    private final ObjectMapper objectMapper;
    private final ItemDeInventarioMapper itemDeInventarioMapper;
    private final ProveedorMapper proveedorMapper;

    @Autowired
    public ProveedorDeItemMapper(ObjectMapper objectMapper, ItemDeInventarioMapper itemDeInventarioMapper, ProveedorMapper proveedorMapper) {
        this.objectMapper = objectMapper;
        this.itemDeInventarioMapper = itemDeInventarioMapper;
        this.proveedorMapper = proveedorMapper;
    }

    // Mapea una lista de ProveedorDeItem a AsociacionProveedorDeItemResponseDTO
    public List<ProveedorDeITemResponseDTO> mapToProveedorDeItemResponseDTO(List<ProveedorDeItem> asociaciones) {
        return asociaciones.stream()
                .map(this::mapToProveedorDeItemResponseDTO)
                .collect(Collectors.toList());
    }

    // Mapea un AsociacionProveedorDeItem a AsociacionProveedorDeItemResponseDTO
    public ProveedorDeITemResponseDTO mapToProveedorDeItemResponseDTO(ProveedorDeItem asociacion) {
        return ProveedorDeITemResponseDTO.builder()
                .item(itemDeInventarioMapper.mapToItemDeInventarioDTO(asociacion.getItemDeInventario()))
                .proveedor(proveedorMapper.mapToProveedorResponseDTO(asociacion.getProveedor()))
                .precio(asociacion.getPrecio())
                .build();
    }

    // Mapea un AsociacionProveedorDeItemResponseDTO a AsociacionProveedorDeItem
    public ProveedorDeItem mapToProveedorDeItem(ProveedorDeITemResponseDTO asociacionDTO) {
        return ProveedorDeItem.builder()
                .itemDeInventario(itemDeInventarioMapper.mapToItemDeInventario(asociacionDTO.getItem()))
                .proveedor(proveedorMapper.mapToProveedor(asociacionDTO.getProveedor()))
                .precio(asociacionDTO.getPrecio())
                .build();
    }
}