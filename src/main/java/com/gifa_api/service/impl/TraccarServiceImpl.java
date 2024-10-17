package com.gifa_api.service.impl;

import com.gifa_api.client.ITraccarCliente;
import com.gifa_api.dto.traccar.CrearDispositivoRequestDTO;
import com.gifa_api.dto.traccar.ObtenerDispositivoRequestDTO;
import com.gifa_api.dto.traccar.PosicionDispositivoDTO;
import com.gifa_api.exception.NotFoundException;
import com.gifa_api.model.Dispositivo;
import com.gifa_api.model.ItemDeInventario;
import com.gifa_api.model.Posicion;
import com.gifa_api.repository.IDispositivoRepository;
import com.gifa_api.repository.IPosicionRepository;
import com.gifa_api.service.ITraccarService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TraccarServiceImpl implements ITraccarService {

    private final ITraccarCliente traccarCliente;

    private final IDispositivoRepository dispositivoRepository;

    @Override
    public void crearDispositivo(CrearDispositivoRequestDTO crearDispositivoRequestDTO) {
        traccarCliente.postCrearDispositivoTraccar(crearDispositivoRequestDTO);
    }

    @Override
    public List<ObtenerDispositivoRequestDTO> obtenerDispositivos() {
        return traccarCliente.getDispositivos();
    }









}
