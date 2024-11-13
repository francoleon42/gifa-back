package com.gifa_api.service;

import com.gifa_api.dto.pedido.CrearPedidoDTO;

import com.gifa_api.dto.pedido.PedidoResponseDTO;

import java.util.List;

public interface IPedidoService {

    void crearPedidoManual(CrearPedidoDTO pedidoManualDTO);
    List<PedidoResponseDTO> obtenerPedidos();
    void hacerPedidoAutomatico(Integer idItem);
    List<PedidoResponseDTO> obtenerPedidosAceptados();
    List<PedidoResponseDTO> obtenerPedidosRechazadosYpendientesYpresupuestoInsuficiente();
    void confirmarPedidoRecibido(Integer idPedido);
}
