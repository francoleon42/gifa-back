package com.gifa_api.service;

import com.gifa_api.dto.proveedoresYPedidos.AsociacionProveedorDeITemDTO;

public interface IProveedorDeItemService {

    void asociarProveedorAItem(AsociacionProveedorDeITemDTO asociacionProveedorDeItemDTO);
}
