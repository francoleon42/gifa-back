package com.gifa_api.utils.mappers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gifa_api.dto.gestorOperacional.GestorOperacionalResponseDTO;
import com.gifa_api.model.GestorOperacional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GestorOperacionalMapper {
    private final ObjectMapper objectMapper;

    @Autowired
    public GestorOperacionalMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public GestorOperacionalResponseDTO obtenerGestorOperacionalDTO(GestorOperacional gestorDePedidos) {
        if (gestorDePedidos == null) {
            return null;
        }

        return GestorOperacionalResponseDTO
                 .builder()
                 .presupuesto(gestorDePedidos.getPresupuesto())
                .consumoDeLitrosPorKm(gestorDePedidos.getConsumoDeLitrosPorKm())
                 .build();
    }

}
