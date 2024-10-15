package com.gifa_api.service;

import com.gifa_api.dto.traccar.CrearDispositivoRequestDTO;
import com.gifa_api.model.Dispositivo;

public interface IDispositivoService {
    void crearDispositivo(CrearDispositivoRequestDTO crearDispositivoRequestDTO,Integer idVehiculo);
}
