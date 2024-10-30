package com.gifa_api.dto.pedido;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CrearPedidoDTO {
    Integer cantidad;
    String motivo;
    Integer idItem;
}
