package com.gifa_api.dto.pedido;

import com.gifa_api.dto.item.ItemDeInventarioDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PedidoResponseDTO {
    LocalDate fecha;
    Integer cantidad;
    String motivo;
    ItemDeInventarioDTO item;
    String estadoPedido;
}
