package com.gifa_api.service;

import com.gifa_api.dto.RegistarVehiculoDTO;

public interface IVehiculoService {

    void registrar(RegistarVehiculoDTO registarVehiculoDTO) throws Exception;
    void inhabilitar(Integer idVehiculoToInhabilitar) throws Exception;
    void habilitar(Integer idVehiculoToInhabilitar) throws Exception;
}
