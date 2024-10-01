package com.gifa_api.service;

import com.gifa_api.dto.RegistarVehiculoDTO;
import com.gifa_api.model.Vehiculo;

import java.util.List;

public interface IVehiculoService {
    List<Vehiculo> getVehiculos();
    void registrar(RegistarVehiculoDTO registarVehiculoDTO) throws Exception;
    void inhabilitar(Integer idVehiculoToInhabilitar) throws Exception;
    void habilitar(Integer idVehiculoToInhabilitar) throws Exception;
}
