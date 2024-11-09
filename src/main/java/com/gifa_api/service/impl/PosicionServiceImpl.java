package com.gifa_api.service.impl;

import com.gifa_api.client.ITraccarCliente;
import com.gifa_api.dto.traccar.DispositivoResponseDTO;
import com.gifa_api.dto.traccar.PosicionDispositivoDTO;
import com.gifa_api.dto.traccar.PosicionResponseDTO;
import com.gifa_api.model.Dispositivo;
import com.gifa_api.model.Posicion;
import com.gifa_api.repository.IPosicionRepository;
import com.gifa_api.service.IDispositivoService;
import com.gifa_api.service.IPosicionService;
import com.gifa_api.service.ITraccarService;
import com.gifa_api.utils.mappers.PosicionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PosicionServiceImpl implements IPosicionService {
    private final IPosicionRepository posicionRepository;
    private final ITraccarCliente traccarCliente;
    private final ITraccarService traccarService;
    private final IDispositivoService dispositivoService;
    private final PosicionMapper posicionMapper;

    @Scheduled(fixedRate = 8640)
    private void actualizarPosicionesDeDispositivo() {

        List<DispositivoResponseDTO> dispositivosDTO = traccarService.obtenerDispositivos();
        for (DispositivoResponseDTO dispositivoDTO : dispositivosDTO) {
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

            // Umbrales de diferencia para latitud y longitud  ( umbral 30 metros)
            double umbralLatitud = 0.00027;
            double umbralLongitud = 0.000324;

            if (diferenciaLatitud > umbralLatitud || diferenciaLongitud > umbralLongitud) {
                return true; // hay una diferencia significativa entre la ultima posicion y la actual
            } else {
                return false; // no hay una diferencia significativa
            }
        }
    }


    @Override
    public List<PosicionResponseDTO> getPosicionesDeDispositivo(String unicoID) {
        return posicionMapper.toPosicionResponseDTOList(posicionRepository.findByUnicoId(unicoID));
    }

    @Override
    public List<Posicion> getPosicionesDeDispositivoDespuesDeFecha(String unicoID, OffsetDateTime fecha) {
        return posicionRepository.findByUnicoIdAndDespuesFecha(unicoID, fecha);
    }
}



