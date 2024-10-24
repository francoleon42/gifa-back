package com.gifa_api.service;

import com.gifa_api.dto.proveedoresYPedidos.CrearPedidoDTO;

import com.gifa_api.dto.proveedoresYPedidos.PedidoResponseDTO;

import java.util.List;

public interface IPedidoService {

    void createPedido(CrearPedidoDTO pedidoManualDTO);
    List<PedidoResponseDTO> obtenerPedidos();
    void hacerPedidos(Integer idItem);
    List<PedidoResponseDTO> obtenerPedidosAceptados();
    List<PedidoResponseDTO> obtenerPedidosRechazadosYpendientes();
    void confirmarPedidoRecibido(Integer idPedido);
}
