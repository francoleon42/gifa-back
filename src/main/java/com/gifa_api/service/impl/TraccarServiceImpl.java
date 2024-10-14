package com.gifa_api.service.impl;

import com.gifa_api.client.ITraccarCliente;
import com.gifa_api.dto.traccar.CrearDispositivoRequestDTO;
import com.gifa_api.dto.traccar.PosicionDispositivoDTO;
import com.gifa_api.service.ITraccarService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TraccarServiceImpl implements ITraccarService {

    private final ITraccarCliente traccarCliente;
    @Override
    public void crearDispositivo(CrearDispositivoRequestDTO crearDispositivoRequestDTO) {
        traccarCliente.postCrearDispositivoTraccar(crearDispositivoRequestDTO);
    }

    @Scheduled(fixedRate = 1000)
    private void obtnerPosiciones(){
        List<PosicionDispositivoDTO> posiciones = traccarCliente.getPosicionDispositivoTraccar(123);
        for(PosicionDispositivoDTO posicion : posiciones){
            System.out.println(posicion.getLongitude());
        }
    }


}
