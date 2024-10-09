package com.gifa_api.service;

import com.gifa_api.dto.vehiculo.ListaVehiculosResponseDTO;
import com.gifa_api.dto.vehiculo.RegistarVehiculoDTO;

public interface IVehiculoService {
    ListaVehiculosResponseDTO getVehiculos();
    void registrar(RegistarVehiculoDTO registarVehiculoDTO);
    void inhabilitar(Integer idVehiculoToInhabilitar);
    void habilitar(Integer idVehiculoToInhabilitar);
}
