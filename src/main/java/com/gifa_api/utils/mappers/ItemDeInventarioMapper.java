package com.gifa_api.utils.mappers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gifa_api.dto.item.ItemDeInventarioDTO;
import com.gifa_api.model.ItemDeInventario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ItemDeInventarioMapper {

    private final ObjectMapper objectMapper;

    @Autowired
    public ItemDeInventarioMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }


    public List<ItemDeInventarioDTO> mapToItemDeInventarioDTO(List<ItemDeInventario> items) {
        return items.stream()
                .map(this::mapToItemDeInventarioDTO)
                .collect(Collectors.toList());
    }


    public ItemDeInventarioDTO mapToItemDeInventarioDTO(ItemDeInventario item) {
        return ItemDeInventarioDTO.builder()
                .id(item.getId())
                .nombre(item.getNombre())
                .umbral(item.getUmbral())
                .stock(item.getStock())
                .cantCompraAutomatica(item.getCantCompraAutomatica())
                .build();
    }


    public ItemDeInventario mapToItemDeInventario(ItemDeInventarioDTO itemDTO) {
        return ItemDeInventario.builder()
                .nombre(itemDTO.getNombre())
                .umbral(itemDTO.getUmbral())
                .stock(itemDTO.getStock())
                .build();
    }
}
