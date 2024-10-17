package com.gifa_api.service.impl;

import com.gifa_api.client.ITraccarCliente;
import com.gifa_api.dto.traccar.ObtenerDispositivoRequestDTO;
import com.gifa_api.dto.traccar.PosicionDispositivoDTO;
import com.gifa_api.exception.NotFoundException;
import com.gifa_api.model.Dispositivo;
import com.gifa_api.model.Posicion;
import com.gifa_api.repository.IDispositivoRepository;
import com.gifa_api.repository.IPosicionRepository;
import com.gifa_api.service.IDispositivoService;
import com.gifa_api.service.IPosicionService;
import com.gifa_api.service.ITraccarService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PosicionServiceImpl implements IPosicionService {
    private final IPosicionRepository posicionRepository;
    private final ITraccarCliente traccarCliente;
    private final ITraccarService traccarService;
    private final IDispositivoService dispositivoService;

    //optimizar con cache
    // y que agregue cada una diferencia muy notable de longitud y latitud
    @Scheduled(fixedRate = 9999)
    private void actualizarPosicionesDeDispositivo() {

        // sacar lo de por mess para que sean todas en general.
        String from = getStartOfMonthUTC();
        String to = getEndOfMonthUTC();

        List<ObtenerDispositivoRequestDTO> dispositivosDTO = traccarService.obtenerDispositivos();
        for (ObtenerDispositivoRequestDTO dispositivoDTO : dispositivosDTO) {
            Dispositivo dispositivo = dispositivoService.obtenerDispositivo(dispositivoDTO.getUniqueId());
            List<PosicionDispositivoDTO> posicionesDeDispositivio = traccarCliente.getPosicionDispositivoTraccar(dispositivoDTO.getId(), from, to);
            for (PosicionDispositivoDTO posicionDTO : posicionesDeDispositivio) {

                Posicion posicion = Posicion
                        .builder()
                        .latitude(posicionDTO.getLatitude())
                        .dispositivo(dispositivo)
                        .longitude(posicionDTO.getLongitude())
                        .fechaHora(posicionDTO.getServerTime())
                        .build();
                posicionRepository.save(posicion);
            }
        }

        dispositivoService.actualizarKilometrajeDeVehiculos();
    }

    private String getStartOfMonthUTC() {
        LocalDateTime now = LocalDateTime.now(ZoneOffset.UTC);
        LocalDateTime startOfMonth = now.withDayOfMonth(1).toLocalDate().atStartOfDay();
        return startOfMonth.toInstant(ZoneOffset.UTC).toString();
    }
    private String getEndOfMonthUTC() {
        LocalDateTime now = LocalDateTime.now(ZoneOffset.UTC);
        LocalDateTime endOfMonth = now.withDayOfMonth(now.toLocalDate().lengthOfMonth())
                .withHour(23)
                .withMinute(59)
                .withSecond(59);
        return endOfMonth.toInstant(ZoneOffset.UTC).toString();
    }

    @Override
    public List<Posicion> getPosicionesDeDispositivo(String unicoID) {
        return posicionRepository.findByUnicoId(unicoID);
    }

    @Override
    public List<Posicion> getPosicionesDeDispositivoDespuesDeFecha(String unicoID, OffsetDateTime fecha) {
        return List.of();
    }
}



