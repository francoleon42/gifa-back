package com.gifa_api.service;

import com.gifa_api.model.Posicion;

import java.time.OffsetDateTime;
import java.util.List;

public interface IPosicionService {
    List<Posicion> getPosicionesDeDispositivo(String unicoID);
    List<Posicion> getPosicionesDeDispositivoDespuesDeFecha(String unicoID, OffsetDateTime fecha);

}
