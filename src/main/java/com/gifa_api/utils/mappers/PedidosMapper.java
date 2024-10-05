package com.gifa_api.utils.mappers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gifa_api.dto.proveedoresYPedidos.PedidoDTO;
import com.gifa_api.model.Pedido;
import com.gifa_api.utils.enums.EstadoPedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PedidosMapper {
    private final ObjectMapper objectMapper;

    @Autowired
    public PedidosMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    // Mapea una lista de pedidos a una lista de PedidoDTO
    public List<PedidoDTO> mapToPedidoDTO(List<Pedido> pedidos) {
        return pedidos.stream()
                .map(this::mapToPedidoDTO)
                .collect(Collectors.toList());
    }

    // Mapea un Pedido a PedidoDTO
    public PedidoDTO mapToPedidoDTO(Pedido pedido) {
        return PedidoDTO.builder()
                .fecha(pedido.getFecha())
                .cantidad(pedido.getCantidad())
                .motivo(pedido.getMotivo())
                .item(pedido.getItem() != null ? pedido.getItem().getNombre() : null)  // Asumiendo que ItemDeInventario tiene un método getNombre()
                .estadoPedido(pedido.getEstadoPedido().name())
                .build();
    }

    // Mapea un PedidoDTO a Pedido
    public Pedido mapToPedido(PedidoDTO pedidoDTO) {
        return Pedido.builder()
                .fecha(pedidoDTO.getFecha())
                .cantidad(pedidoDTO.getCantidad())
                .motivo(pedidoDTO.getMotivo())
                // Debes establecer el item correctamente, posiblemente mediante un repositorio
                .estadoPedido(EstadoPedido.valueOf(pedidoDTO.getEstadoPedido()))  // Asegúrate de manejar las conversiones adecuadamente
                .build();
    }
}
