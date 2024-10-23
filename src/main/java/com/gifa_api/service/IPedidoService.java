package com.gifa_api.service;

import com.gifa_api.dto.proveedoresYPedidos.PedidoManualDTO;
import com.gifa_api.dto.proveedoresYPedidos.PedidoResponseDTO;

import java.util.List;

public interface IPedidoService {

    void createPedido(PedidoManualDTO pedidoManualDTO);
    List<PedidoResponseDTO> obtenerPedidos();
}
