package com.gifa_api.service;

import com.gifa_api.model.ItemDeInventario;
import com.gifa_api.model.Pedido;

public interface IPedidoService {

    void createPedido(ItemDeInventario itemDeInventario,Integer cantidad);
//    PedidosDTO obtenerPedidos();
}
