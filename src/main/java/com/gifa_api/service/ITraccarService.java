package com.gifa_api.service;

import com.gifa_api.dto.traccar.InconsistenciasKMconCombustiblesResponseDTO;
import com.gifa_api.dto.traccar.DispositivoResponseDTO;
import com.gifa_api.dto.traccar.KilometrosResponseDTO;
import com.gifa_api.model.Dispositivo;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;

public interface ITraccarService {

//    void crearDispositivo(CrearDispositivoRequestDTO crearDispositivoRequestDTO);

    void crearDispositivo(Dispositivo dispositivo);
    Integer obtenerdeviceIdByUniqueId(String uniqueId);
    List<DispositivoResponseDTO> obtenerDispositivos();

    List<InconsistenciasKMconCombustiblesResponseDTO> getInconsistencias(LocalDate fecha);

    KilometrosResponseDTO getKilometros(Integer deviceId, OffsetDateTime from, OffsetDateTime to);
}
