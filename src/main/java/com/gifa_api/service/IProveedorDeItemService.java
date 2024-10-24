package com.gifa_api.service;

import com.gifa_api.dto.proveedoresYPedidos.AsociacionProveedorDeITemRequestDTO;
import com.gifa_api.model.ProveedorDeItem;

public interface IProveedorDeItemService {

    void asociarProveedorAItem(AsociacionProveedorDeITemRequestDTO asociacionProveedorDeItemRequestDTO);
    ProveedorDeItem proveedorMasEconomico(Integer idItem);
}
