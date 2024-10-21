package com.gifa_api.utils.mappers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gifa_api.dto.GestorOperacionalDTO;
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

    public GestorOperacionalDTO obtenerGestorOperacionalDTO(GestorOperacional gestorDePedidos) {
        if (gestorDePedidos == null) {
            return null;
        }

        GestorOperacionalDTO gestorDePedidosDTO = GestorOperacionalDTO
                 .builder()
                 .cantDePedidoAutomatico(gestorDePedidos.getCantDePedidoAutomatico())
                 .presupuesto(gestorDePedidos.getPresupuesto())
                 .build();
        return  gestorDePedidosDTO;
    }

}
