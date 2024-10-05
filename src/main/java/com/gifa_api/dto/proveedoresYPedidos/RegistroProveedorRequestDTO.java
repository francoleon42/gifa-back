package com.gifa_api.dto.proveedoresYPedidos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegistroProveedorRequestDTO {

    String nombre;
    String email;
}
