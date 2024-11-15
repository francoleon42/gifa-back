package com.gifa_api.service.impl;

import com.gifa_api.dto.traccar.CrearDispositivoRequestDTO;
import com.gifa_api.dto.traccar.DispositivoResponseDTO;
import com.gifa_api.exception.BadRequestException;
import com.gifa_api.exception.NotFoundException;
import com.gifa_api.model.Dispositivo;
import com.gifa_api.model.Vehiculo;
import com.gifa_api.repository.IDispositivoRepository;
import com.gifa_api.repository.IPosicionRepository;
import com.gifa_api.repository.IVehiculoRepository;
import com.gifa_api.service.IDispositivoService;
import com.gifa_api.service.IKilometrajeVehiculoService;
import com.gifa_api.service.ITraccarService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DispositivoServiceImpl implements IDispositivoService {
    private final IDispositivoRepository dispositivoRepository;
    private final IVehiculoRepository vehiculoRepository;
    private final IPosicionRepository posicionRepository;
    private final IKilometrajeVehiculoService kilometrajeVehiculoService;
    private final ITraccarService traccarService;

    @Override
    public void crearDispositivo(CrearDispositivoRequestDTO crearDispositivoRequestDTO, Integer idVehiculo) {
        // Validar el DTO
        validarCrearDispositivoRequestDTO(crearDispositivoRequestDTO);
        Vehiculo vehiculo = vehiculoRepository.findById(idVehiculo)
                .orElseThrow(() -> new NotFoundException("No se encontró el vehiculo con id: " + idVehiculo));
        Dispositivo dispositivo = Dispositivo
                .builder()
                .nombre(crearDispositivoRequestDTO.getName())
                .unicoId(crearDispositivoRequestDTO.getUniqueId())
                .vehiculo(vehiculo)
                .build();

        vehiculo.addDispositivo(dispositivo);
        vehiculoRepository.save(vehiculo);
    }

    @Override
    public Dispositivo obtenerDispositivo(String unicoId) {
        Dispositivo dispositivo = dispositivoRepository.findByUnicoId(unicoId)
                .orElseThrow(() -> new NotFoundException("No se encontró el dispositivo con id: "));
        return dispositivo;
    }


    @Scheduled(fixedRate = 8640)
    private void actualizarKilometrajeDeVehiculos() {
        for (DispositivoResponseDTO dispositivoResponseDTO : traccarService.obtenerDispositivos()) {

            OffsetDateTime from = OffsetDateTime.parse("1970-01-01T00:00:00Z");
            OffsetDateTime to = OffsetDateTime.parse("2100-01-01T00:00:00Z");
            Integer metrosActual = traccarService.getKilometros(dispositivoResponseDTO.getId(), from, to).getDistance();
            Vehiculo vehiculo = dispositivoRepository.findVehiculoDeDispositivo(dispositivoResponseDTO.getUniqueId())
                    .orElseThrow(() -> new NotFoundException("No se encontró el vehiculo con id: " + dispositivoResponseDTO.getUniqueId()));

            double kmActual = metrosActual / 1000.0;
            double kilometrosAgregados = kmActual - vehiculo.getKilometraje();

            kilometrajeVehiculoService.addKilometrajeVehiculo(kilometrosAgregados, OffsetDateTime.now(), vehiculo.getId());
            vehiculo.actualizarKilometraje(kmActual);
            vehiculoRepository.save(vehiculo);


        }
    }

    private int formulaDeHaversine(List<Posicion> posiciones) {
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

        return kilometros;
    }

    private void validarCrearDispositivoRequestDTO(CrearDispositivoRequestDTO crearDispositivoRequestDTO) {
        if (crearDispositivoRequestDTO.getName() == null || crearDispositivoRequestDTO.getName().trim().isEmpty()) {
            throw new BadRequestException("El nombre del dispositivo no puede estar vacío.");
        }
        if (crearDispositivoRequestDTO.getUniqueId() == null || crearDispositivoRequestDTO.getUniqueId().trim().isEmpty()) {
            throw new BadRequestException("El uniqueId del dispositivo no puede estar vacío.");
        }
    }

}
