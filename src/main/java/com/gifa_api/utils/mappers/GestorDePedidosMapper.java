package com.gifa_api.utils.mappers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gifa_api.dto.proveedoresYPedidos.GestorDePedidosDTO;
import com.gifa_api.model.GestorDePedidos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GestorDePedidosMapper {
    private final ObjectMapper objectMapper;

    @Autowired
    public GestorDePedidosMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public GestorDePedidosDTO obtenerGestorDePedidosDTO(GestorDePedidos gestorDePedidos) {
        if (gestorDePedidos == null) {
            return null;
        }

         GestorDePedidosDTO gestorDePedidosDTO = GestorDePedidosDTO
                 .builder()
                 .cantDePedidoAutomatico(gestorDePedidos.getId())
                 .presupuesto(gestorDePedidos.getPresupuesto())
                 .build();
        return  gestorDePedidosDTO;
    }

}
