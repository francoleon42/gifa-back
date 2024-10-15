package com.gifa_api.service.impl;

import com.gifa_api.dto.traccar.CrearDispositivoRequestDTO;
import com.gifa_api.exception.NotFoundException;
import com.gifa_api.model.Dispositivo;
import com.gifa_api.model.Posicion;
import com.gifa_api.model.Vehiculo;
import com.gifa_api.repository.IDispositivoRepository;
import com.gifa_api.repository.IPosicionRepository;
import com.gifa_api.repository.IVehiculoRepository;
import com.gifa_api.service.IDispositivoService;
import com.gifa_api.service.IVehiculoService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DispositivoServiceImpl implements IDispositivoService {
    private final IDispositivoRepository dispositivoRepository;
    private final IVehiculoRepository vehiculoRepository;
    private final IPosicionRepository posicionRepository;
    @Override
    public void crearDispositivo(CrearDispositivoRequestDTO crearDispositivoRequestDTO, Integer idVehiculo) {
        Vehiculo vehiculo = vehiculoRepository.findById(idVehiculo)
                .orElseThrow(() -> new NotFoundException("No se encontró el vehiculo con id: " + idVehiculo));
        Dispositivo dispositivo = Dispositivo
                .builder()
                .nombre(crearDispositivoRequestDTO.getName())
                .unicoId(crearDispositivoRequestDTO.getUniqueId())
                .vehiculo(vehiculo)
                .build();
        dispositivoRepository.save(dispositivo);
    }


    public int calcularDistanciaRecorrida(String unicoIdDeDispositivo ) {
        List<Posicion> posiciones = posicionRepository.findByUnicoId(unicoIdDeDispositivo);

        double distanciaTotal = 0.0;

        for (int i = 1; i < posiciones.size(); i++) {
            Posicion pos1 = posiciones.get(i - 1);
            Posicion pos2 = posiciones.get(i);

            double lat1 = Math.toRadians(pos1.getLatitude());
            double lon1 = Math.toRadians(pos1.getLongitude());
            double lat2 = Math.toRadians(pos2.getLatitude());
            double lon2 = Math.toRadians(pos2.getLongitude());

            double deltaLat = lat2 - lat1;
            double deltaLon = lon2 - lon1;

            // Fórmula del Haversine
            double a = Math.sin(deltaLat / 2) * Math.sin(deltaLat / 2) +
                    Math.cos(lat1) * Math.cos(lat2) *
                            Math.sin(deltaLon / 2) * Math.sin(deltaLon / 2);
            double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
            double distancia = 6371 * c; // Radio de la Tierra en km

            distanciaTotal += distancia;
        }
        int kilometros = (int) distanciaTotal;
//        int metros = (int) ((distanciaTotal - kilometros) * 1000); // Convertir a metros y obtener el resto

//        // Formatear la distancia en el formato deseado "km,metros"
//        String distanciaFormateada = kilometros + "," + String.format("%03d", metros);

        return kilometros;
    }


}
