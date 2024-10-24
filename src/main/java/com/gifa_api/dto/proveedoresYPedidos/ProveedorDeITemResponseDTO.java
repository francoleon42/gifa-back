package com.gifa_api.dto.proveedoresYPedidos;

import com.gifa_api.dto.item.ItemDeInventarioDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProveedorDeITemResponseDTO {

    ItemDeInventarioDTO item;
    ProveedorResponseDTO proveedor;
    Double precio;

}
