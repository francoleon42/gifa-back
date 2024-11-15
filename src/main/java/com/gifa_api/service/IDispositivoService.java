package com.gifa_api.service;

import com.gifa_api.dto.traccar.CrearDispositivoRequestDTO;
import com.gifa_api.model.Dispositivo;

import java.time.LocalDate;
import java.time.OffsetDateTime;

public interface IDispositivoService {
    void crearDispositivo(CrearDispositivoRequestDTO crearDispositivoRequestDTO,Integer idVehiculo);
    Dispositivo obtenerDispositivo(String unicoId);
//    void actualizarKilometrajeDeVehiculos();
double calcularKmDeDispositivoEntreFechas(String unicoId, OffsetDateTime from,OffsetDateTime to );

}
