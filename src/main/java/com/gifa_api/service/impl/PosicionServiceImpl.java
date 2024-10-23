package com.gifa_api.service.impl;

import com.gifa_api.client.ITraccarCliente;
import com.gifa_api.dto.traccar.ObtenerDispositivoRequestDTO;
import com.gifa_api.dto.traccar.PosicionDispositivoDTO;
import com.gifa_api.exception.NotFoundException;
import com.gifa_api.model.Dispositivo;
import com.gifa_api.model.Posicion;
import com.gifa_api.repository.IPosicionRepository;
import com.gifa_api.service.IDispositivoService;
import com.gifa_api.service.IPosicionService;
import com.gifa_api.service.ITraccarService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PosicionServiceImpl implements IPosicionService {
    private final IPosicionRepository posicionRepository;
    private final ITraccarCliente traccarCliente;
    private final ITraccarService traccarService;
    private final IDispositivoService dispositivoService;

    //@Scheduled(fixedRate = 8640)
    private void actualizarPosicionesDeDispositivo() {

        List<ObtenerDispositivoRequestDTO> dispositivosDTO = traccarService.obtenerDispositivos();
        for (ObtenerDispositivoRequestDTO dispositivoDTO : dispositivosDTO) {
            Dispositivo dispositivo = dispositivoService.obtenerDispositivo(dispositivoDTO.getUniqueId());
            List<PosicionDispositivoDTO> posicionesDeDispositivio = traccarCliente.getPosicionDispositivoTraccar(dispositivoDTO.getId());
            for (PosicionDispositivoDTO posicionDTO : posicionesDeDispositivio) {

                if (!estaPosicion(dispositivoDTO.getUniqueId(), posicionDTO.getServerTime()) && suficienteDiferencia(dispositivoDTO.getUniqueId(), posicionDTO.getLatitude(), posicionDTO.getLongitude())) {
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
        }
    }

    private boolean estaPosicion(String unicoId, OffsetDateTime fecha) {
        return posicionRepository.obtenerPosicionDeDispositivoPorFecha(unicoId, fecha).isPresent();
    }

    private boolean suficienteDiferencia(String dispositivoId, double latitude, double longitude) {
        List<Posicion> posiciones = posicionRepository.obtenerUltimaPosicion(dispositivoId);
        if (posiciones.isEmpty()) {
            return true;  // Si no hay posición previa, retornamos true
        } else {
            Posicion ultimaPosicion = posiciones.get(0);
            double diferenciaLatitud = Math.abs(ultimaPosicion.getLatitude() - latitude);
            double diferenciaLongitud = Math.abs(ultimaPosicion.getLongitude() - longitude);

            // Umbrales de diferencia para latitud y longitud  ( umbral 60 metros)
            double umbralLatitud = 0.000539;
            double umbralLongitud = 0.000648;

            if (diferenciaLatitud > umbralLatitud || diferenciaLongitud > umbralLongitud) {
                return true; // hay una diferencia significativa entre la ultima posicion y la actual
            } else {
                return false; // no hay una diferencia significativa
            }
        }
    }


    @Override
    public List<Posicion> getPosicionesDeDispositivo(String unicoID) {
        return posicionRepository.findByUnicoId(unicoID);
    }

    @Override
    public List<Posicion> getPosicionesDeDispositivoDespuesDeFecha(String unicoID, OffsetDateTime fecha) {
        return posicionRepository.findByUnicoIdAndDespuesFecha(unicoID, fecha);
    }
}



