package com.gifa_api.service.impl;

import com.gifa_api.client.ITraccarCliente;
import com.gifa_api.dto.traccar.CrearDispositivoRequestDTO;
import com.gifa_api.dto.traccar.ObtenerDispositivoRequestDTO;
import com.gifa_api.dto.traccar.PosicionDispositivoDTO;
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

    @Scheduled(fixedRate = 9999)
    private void obtenerPosicionesDelMes() {

        String from = getStartOfMonthUTC();
        String to = getEndOfMonthUTC();


        List<ObtenerDispositivoRequestDTO> dispositivos  = traccarCliente.getDispositivos();
        for(ObtenerDispositivoRequestDTO dispositivo : dispositivos) {
            List<PosicionDispositivoDTO> posiciones = traccarCliente.getPosicionDispositivoTraccar(dispositivo.getId(), from, to);
            System.out.println("---------Dispositivo-------"+dispositivo.getId());
            for (PosicionDispositivoDTO posicion : posiciones) {

                System.out.println("Longitud: " + posicion.getLongitude());
                System.out.println("Altitud: " + posicion.getLatitude());
            }
        }
//        Integer deviceId = 25;


        // Obtener las posiciones utilizando el cliente Traccar con los valores hardcodeados

//        System.out.println("-------------------------");
//        // Iterar sobre las posiciones y hacer algo con ellas, por ejemplo, imprimir la longitud
//        for (PosicionDispositivoDTO posicion : posiciones) {
//
//            System.out.println("Longitud: " + posicion.getLongitude() );
//            System.out.println("Altitud: " + posicion.getLatitude() );
//        }
    }





}
