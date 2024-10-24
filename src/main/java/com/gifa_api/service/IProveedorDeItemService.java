package com.gifa_api.service;



import com.gifa_api.dto.proveedoresYPedidos.ProveedorDeITemRequestDTO;
import com.gifa_api.dto.proveedoresYPedidos.ProveedorDeITemResponseDTO;
import com.gifa_api.model.ProveedorDeItem;

import java.util.List;

public interface IProveedorDeItemService {

    void asociarProveedorAItem(ProveedorDeITemRequestDTO proveedorDeItemRequestDTO);
    ProveedorDeItem proveedorMasEconomico(Integer idItem);
    List<ProveedorDeITemResponseDTO> obtenerAll();
}
