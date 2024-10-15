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
    private final IPosicionRepository posicionRepository;
    private final IDispositivoRepository dispositivoRepository;

    @Override
    public void crearDispositivo(CrearDispositivoRequestDTO crearDispositivoRequestDTO) {
        traccarCliente.postCrearDispositivoTraccar(crearDispositivoRequestDTO);
    }

    @Override
    public List<ObtenerDispositivoRequestDTO> obtenerDispositivos() {
        return traccarCliente.getDispositivos();
    }


    public static String getStartOfMonthUTC() {
        LocalDateTime now = LocalDateTime.now(ZoneOffset.UTC);
        LocalDateTime startOfMonth = now.withDayOfMonth(1).toLocalDate().atStartOfDay();
        return startOfMonth.toInstant(ZoneOffset.UTC).toString();
    }

    private static String getEndOfMonthUTC() {
        LocalDateTime now = LocalDateTime.now(ZoneOffset.UTC);
        LocalDateTime endOfMonth = now.withDayOfMonth(now.toLocalDate().lengthOfMonth())
                .withHour(23)
                .withMinute(59)
                .withSecond(59);
        return endOfMonth.toInstant(ZoneOffset.UTC).toString();
    }

    // IMPORTANTE
    //optimizar con cache
    // y que agregue cada una diferencia muy notable de longitud y latitud
    @Scheduled(fixedRate = 9999)
    private void obtenerPosiciones() {

        // sacar lo de por mess para que sean todas en general.
        String from = getStartOfMonthUTC();
        String to = getEndOfMonthUTC();

        List<ObtenerDispositivoRequestDTO> dispositivosDTO = traccarCliente.getDispositivos();
        for (ObtenerDispositivoRequestDTO dispositivoDTO : dispositivosDTO) {
            Dispositivo dispositivo = dispositivoRepository.findByUnicoId(dispositivoDTO.getUniqueId())
                    .orElseThrow(() -> new NotFoundException("No se encontró el dispositivo con id: "));
            List<PosicionDispositivoDTO> posicionesDeDispositivio = traccarCliente.getPosicionDispositivoTraccar(dispositivoDTO.getId(), from, to);
            for (PosicionDispositivoDTO posicionDTO : posicionesDeDispositivio) {

                Posicion posicion = Posicion
                        .builder()
                        .latitude(posicionDTO.getLatitude())
                        .dispositivo(dispositivo)
                        .longitude(posicionDTO.getLongitude())
                        .build();
                posicionRepository.save(posicion);
            }
        }

    }


}
