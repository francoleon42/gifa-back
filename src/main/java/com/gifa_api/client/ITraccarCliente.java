package com.gifa_api.client;

import com.gifa_api.dto.traccar.CrearDispositivoRequestDTO;
import com.gifa_api.dto.traccar.CrearDispositivoResponseDTO;
import reactor.core.publisher.Mono;

public interface ITraccarCliente {
    public CrearDispositivoResponseDTO postCrearDispositivoTraccar(CrearDispositivoRequestDTO request) ;
}
