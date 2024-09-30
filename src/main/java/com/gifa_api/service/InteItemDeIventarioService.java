package com.gifa_api.service;

import com.gifa_api.dto.RegistrarItemDeInventarioDTO;

public interface InteItemDeIventarioService {

    void registrar(RegistrarItemDeInventarioDTO registrarItemDeInventarioDTO);
    void utilizarItem(Integer id);
}
