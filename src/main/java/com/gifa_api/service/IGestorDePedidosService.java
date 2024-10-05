package com.gifa_api.service;

import com.gifa_api.dto.proveedoresYPedidos.ActualizarGestorDePedidosDTO;
import com.gifa_api.model.GestorDePedidos;

public interface IGestorDePedidosService {
    GestorDePedidos obtenerGestorDePedidos();
    void actualizarGestorDePedidos(ActualizarGestorDePedidosDTO actualizarGestorDePedidosDTO);
}
