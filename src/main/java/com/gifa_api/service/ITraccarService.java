package com.gifa_api.service;

import com.gifa_api.dto.traccar.CrearDispositivoRequestDTO;
import com.gifa_api.dto.traccar.InconsistenciasKMconCombustiblesResponseDTO;
import com.gifa_api.dto.traccar.ObtenerDispositivoRequestDTO;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;

public interface ITraccarService {

    void crearDispositivo(CrearDispositivoRequestDTO crearDispositivoRequestDTO);
    List<ObtenerDispositivoRequestDTO> obtenerDispositivos();

    List<InconsistenciasKMconCombustiblesResponseDTO> getInconsistencias(LocalDate fecha);
}
