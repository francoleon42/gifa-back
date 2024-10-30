package com.gifa_api.dto.proveedor;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProveedorDeITemRequestDTO {
    Integer idItem;
    Integer idProveedor;
    Double precio;
}
