package com.gifa_api.service;

import com.gifa_api.dto.proveedoresYPedidos.PedidoResponseDTO;

import java.util.List;

public interface IPedidoService {

    void createPedido(Integer idItem,Integer cantidad,String motivo);
    List<PedidoResponseDTO> obtenerPedidos();
}
