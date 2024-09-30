package com.gifa_api.service;

import com.gifa_api.dto.VehiculoDTO;

public interface IVehiculoService {

    void registrarVehiculo(VehiculoDTO vehiculoDTO) throws Exception;
}
