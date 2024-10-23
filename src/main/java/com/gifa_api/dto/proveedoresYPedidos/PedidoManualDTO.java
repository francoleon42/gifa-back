package com.gifa_api.dto.proveedoresYPedidos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PedidoManualDTO {
    Integer cantidad;
    String motivo;
    Integer idItem;
}
