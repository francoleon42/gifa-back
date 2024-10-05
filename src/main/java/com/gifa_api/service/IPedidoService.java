package com.gifa_api.service;

import com.gifa_api.dto.proveedoresYPedidos.PedidoDTO;
import com.gifa_api.model.ItemDeInventario;

import java.util.List;

public interface IPedidoService {

    void createPedido(ItemDeInventario itemDeInventario,Integer cantidad);
    List<PedidoDTO> obtenerPedidos();
}
