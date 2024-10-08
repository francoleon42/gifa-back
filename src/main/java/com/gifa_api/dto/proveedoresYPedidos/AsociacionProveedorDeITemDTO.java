package com.gifa_api.dto.proveedoresYPedidos;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AsociacionProveedorDeITemDTO {
    Integer idItem;
    Integer idProveedor;
    Double precio;
}
