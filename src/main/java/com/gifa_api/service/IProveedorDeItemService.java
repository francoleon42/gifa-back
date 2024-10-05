package com.gifa_api.service;

import com.gifa_api.dto.proveedoresYPedidos.AsociacionProveedorDeITemDTO;
import com.gifa_api.model.Proveedor;
import com.gifa_api.model.ProveedorDeItem;

public interface IProveedorDeItemService {

    void asociarProveedorAItem(AsociacionProveedorDeITemDTO asociacionProveedorDeItemDTO);
    ProveedorDeItem proveedorMasEconomico(Integer idItem);
}
