package com.gifa_api.client;

import com.gifa_api.dto.traccar.CrearDispositivoRequestDTO;
import com.gifa_api.dto.traccar.CrearDispositivoResponseDTO;
import com.gifa_api.dto.traccar.PosicionDispositivoDTO;
import reactor.core.publisher.Mono;

import java.util.List;

public interface ITraccarCliente {
    public CrearDispositivoResponseDTO postCrearDispositivoTraccar(CrearDispositivoRequestDTO request) ;
    List<PosicionDispositivoDTO> getPosicionDispositivoTraccar(Integer deviceId, String from, String to);
}
