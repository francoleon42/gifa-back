package com.gifa_api.service;

import com.gifa_api.dto.item.ItemDeInventarioDTO;

import com.gifa_api.dto.item.ItemDeInventarioRequestDTO;
import com.gifa_api.model.ItemDeInventario;

import java.util.List;

public interface IItemDeIventarioService {

    void registrar(ItemDeInventarioRequestDTO itemDeInventarioDTO);
    void utilizarItem(Integer id);
    ItemDeInventario obtenerById(Integer id);
    List<ItemDeInventarioDTO> obtenerAllitems();
}
