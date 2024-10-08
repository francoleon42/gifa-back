package com.gifa_api.dto.proveedoresYPedidos;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GestorDePedidosDTO {
    Double presupuesto;
    Integer cantDePedidoAutomatico;
}
