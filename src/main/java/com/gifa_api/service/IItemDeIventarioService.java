package com.gifa_api.service;

import com.gifa_api.dto.RegistrarItemDeInventarioDTO;

public interface IItemDeIventarioService {

    void registrar(RegistrarItemDeInventarioDTO registrarItemDeInventarioDTO);
    void utilizarItem(Integer id);
}
