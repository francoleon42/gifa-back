package com.gifa_api.service;

import com.gifa_api.dto.RegistrarItemDeInventarioDTO;
import com.gifa_api.model.ItemDeInventario;

public interface IItemDeIventarioService {

    void registrar(RegistrarItemDeInventarioDTO registrarItemDeInventarioDTO);
    void utilizarItem(Integer id);
    ItemDeInventario obtenerById(Integer id);
}
