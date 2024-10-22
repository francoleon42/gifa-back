package com.gifa_api.service.impl;

import com.gifa_api.client.ITraccarCliente;
import com.gifa_api.dto.traccar.ObtenerDispositivoRequestDTO;
import com.gifa_api.dto.traccar.PosicionDispositivoDTO;
import com.gifa_api.model.Dispositivo;
import com.gifa_api.model.Posicion;
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
    //Validar si la posición es lo suficientemente diferente de la última
    @Scheduled(fixedRate = 8640)
    private void actualizarPosicionesDeDispositivo() {

        List<ObtenerDispositivoRequestDTO> dispositivosDTO = traccarService.obtenerDispositivos();
        for (ObtenerDispositivoRequestDTO dispositivoDTO : dispositivosDTO) {
           Dispositivo dispositivo = dispositivoService.obtenerDispositivo(dispositivoDTO.getUniqueId());
            List<PosicionDispositivoDTO> posicionesDeDispositivio = traccarCliente.getPosicionDispositivoTraccar(dispositivoDTO.getId());
            for (PosicionDispositivoDTO posicionDTO : posicionesDeDispositivio) {

                if(!estaPosicion(dispositivoDTO.getUniqueId(),posicionDTO.getServerTime())){
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
    private boolean estaPosicion(String unicoId,OffsetDateTime fecha) {
        return posicionRepository.obtenerPosicionDeDispositivoPorFecha(unicoId, fecha).isPresent();
    }

    @Override
    public List<Posicion> getPosicionesDeDispositivo(String unicoID) {
        return posicionRepository.findByUnicoId(unicoID);
    }

    @Override
    public List<Posicion> getPosicionesDeDispositivoDespuesDeFecha(String unicoID, OffsetDateTime fecha) {
        return posicionRepository.findByUnicoIdAndDespuesFecha(unicoID,fecha);
    }
}



