package com.gifa_api.client;

import com.gifa_api.dto.traccar.CrearDispositivoRequestDTO;
import com.gifa_api.dto.traccar.CrearDispositivoResponseDTO;
import com.gifa_api.dto.traccar.ObtenerDispositivoRequestDTO;
import com.gifa_api.dto.traccar.PosicionDispositivoDTO;
import com.gifa_api.model.Dispositivo;
import reactor.core.publisher.Mono;

import java.util.List;

public interface ITraccarCliente {
    public CrearDispositivoResponseDTO postCrearDispositivoTraccar(Dispositivo  dispositivo) ;
    List<PosicionDispositivoDTO> getPosicionDispositivoTraccar(Integer deviceId);
    List<ObtenerDispositivoRequestDTO> getDispositivos();
    ObtenerDispositivoRequestDTO getDispositivo(Integer id);
}
