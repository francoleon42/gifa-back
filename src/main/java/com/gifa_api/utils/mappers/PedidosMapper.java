package com.gifa_api.utils.mappers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gifa_api.dto.item.ItemDeInventarioDTO;
import com.gifa_api.dto.pedido.PedidoResponseDTO;
import com.gifa_api.model.ItemDeInventario;
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

    public List<PedidoResponseDTO> mapToPedidoDTO(List<Pedido> pedidos) {
        return pedidos.stream()
                .map(this::mapToPedidoDTO)
                .collect(Collectors.toList());
    }

    public PedidoResponseDTO mapToPedidoDTO(Pedido pedido) {
        return PedidoResponseDTO.builder()
                .fecha(pedido.getFecha())
                .cantidad(pedido.getCantidad())
                .motivo(pedido.getMotivo())
                .item(mapToItemDeInventarioDTO(pedido.getItem()))  // Conversión del Item completo
                .estadoPedido(pedido.getEstadoPedido().name())
                .build();
    }

    private ItemDeInventarioDTO mapToItemDeInventarioDTO(ItemDeInventario item) {
        if (item == null) {
            return null;
        }
        return ItemDeInventarioDTO.builder()
                .id(item.getId())
                .nombre(item.getNombre())
                .umbral(item.getUmbral())
                .stock(item.getStock())
                .cantCompraAutomatica(item.getCantCompraAutomatica())
                .build();
    }

    public Pedido mapToPedido(PedidoResponseDTO pedidoResponseDTO) {
        return Pedido.builder()
                .fecha(pedidoResponseDTO.getFecha())
                .cantidad(pedidoResponseDTO.getCantidad())
                .motivo(pedidoResponseDTO.getMotivo())
                .estadoPedido(EstadoPedido.valueOf(pedidoResponseDTO.getEstadoPedido()))
                .build();
    }
}