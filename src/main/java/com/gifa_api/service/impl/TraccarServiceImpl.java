package com.gifa_api.service.impl;

import com.gifa_api.client.ITraccarCliente;
import com.gifa_api.dto.traccar.CrearDispositivoRequestDTO;
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

    @Scheduled(fixedRate = 9999)
    private List<PosicionDispositivoDTO> obtenerPosiciones() {
        Integer deviceId = 25;

        // Obtener el primer día del mes actual a las 00:00:00 en formato UTC
        LocalDateTime now = LocalDateTime.now(ZoneOffset.UTC);
        LocalDateTime startOfMonth = now.withDayOfMonth(1).toLocalDate().atStartOfDay();

        // Obtener el último día del mes actual a las 23:59:59 en formato UTC
        LocalDateTime endOfMonth = now.withDayOfMonth(now.toLocalDate().lengthOfMonth())
                .withHour(23)
                .withMinute(59)
                .withSecond(59);

        // Formatear las fechas a ISO 8601 (con 'Z' para indicar UTC)
        DateTimeFormatter formatter = DateTimeFormatter.ISO_INSTANT;
        String from = startOfMonth.toInstant(ZoneOffset.UTC).toString();
        String to = endOfMonth.toInstant(ZoneOffset.UTC).toString();

        // Ejemplo de IDs de dispositivos


        // Obtener las posiciones utilizando el cliente Traccar con los valores hardcodeados
        List<PosicionDispositivoDTO> posiciones = traccarCliente.getPosicionDispositivoTraccar(deviceId, from, to);
        System.out.println("-------------------------");
        // Iterar sobre las posiciones y hacer algo con ellas, por ejemplo, imprimir la longitud
        for (PosicionDispositivoDTO posicion : posiciones) {

            System.out.println("Longitud: " + posicion.getLongitude() );
            System.out.println("Altitud: " + posicion.getLatitude() );
        }
        return posiciones;
    }

    


}
