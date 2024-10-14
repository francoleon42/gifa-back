package com.gifa_api.service.impl;

import com.gifa_api.client.ITraccarCliente;
import com.gifa_api.dto.traccar.CrearDispositivoRequestDTO;
import com.gifa_api.service.ITraccarService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TraccarServiceImpl implements ITraccarService {

    private final ITraccarCliente traccarCliente;
    @Override
    public void crearDispositivo(CrearDispositivoRequestDTO crearDispositivoRequestDTO) {
        traccarCliente.postCrearDispositivoTraccar(crearDispositivoRequestDTO);
    }
}
