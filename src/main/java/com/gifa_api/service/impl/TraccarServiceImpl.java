package com.gifa_api.service.impl;

import com.gifa_api.client.ITraccarCliente;
import com.gifa_api.dto.traccar.InconsistenciasKMconCombustiblesResponseDTO;
import com.gifa_api.dto.traccar.DispositivoResponseDTO;
import com.gifa_api.dto.traccar.KilometrosResponseDTO;
import com.gifa_api.dto.traccar.PosicionResponseDTO;
import com.gifa_api.dto.vehiculo.VehiculoResponseDTO;
import com.gifa_api.model.Dispositivo;
import com.gifa_api.model.Vehiculo;
import com.gifa_api.repository.IChoferRepository;
import com.gifa_api.repository.IDispositivoRepository;
import com.gifa_api.repository.IVehiculoRepository;
import com.gifa_api.service.ICargaCombustibleService;
import com.gifa_api.service.IDispositivoService;
import com.gifa_api.service.ITraccarService;
import com.gifa_api.utils.mappers.PosicionMapper;
import com.gifa_api.utils.mappers.VehiculoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TraccarServiceImpl implements ITraccarService {

    private final ITraccarCliente traccarCliente;
    private final ICargaCombustibleService cargaCombustibleService;
    private final IVehiculoRepository vehiculoRepository;
    private final IChoferRepository choferRepository;
    private final IDispositivoRepository dispositivoRepository;
    private final PosicionMapper posicionMapper;
    private final VehiculoMapper vehiculoMapper;


    @Override
    public void crearDispositivo(Dispositivo dispositivo) {
        if (!existeDispositivoEnTraccar(dispositivo.getUnicoId())) {
            traccarCliente.postCrearDispositivoTraccar(dispositivo);
        }

    }


    private boolean existeDispositivoEnTraccar(String uniqueId) {
        for (DispositivoResponseDTO dispositivo : obtenerDispositivos()) {
            if (dispositivo.getUniqueId().equals(uniqueId))
                return true;
        }
        return false;
    }

    @Override
    public List<DispositivoResponseDTO> obtenerDispositivos() {
        List<DispositivoResponseDTO> todosLosDispositivos = traccarCliente.getDispositivos();

        // Filtrar dispositivos que estén en el repositorio de vehículos
        return todosLosDispositivos.stream()
                .filter(dispositivo -> vehiculoRepository.findByPatente(dispositivo.getUniqueId()).isPresent())
                .collect(Collectors.toList());
    }

    @Override
    public List<InconsistenciasKMconCombustiblesResponseDTO> getInconsistencias(OffsetDateTime from, OffsetDateTime to) {

        List<InconsistenciasKMconCombustiblesResponseDTO> inconsistencias = new ArrayList<>();
        for (Vehiculo vehiculo : vehiculoRepository.findAll()) {


            double kmRecorridos = calcularKmDeDispositivoEntreFechas(vehiculo.getDispositivo().getUnicoId(), from, to);
            double litrosCargados = cargaCombustibleService.combustibleCargadoEntreFechas(vehiculo.getTarjeta().getNumero(), from, to);

            if (calculoDeCombustiblePorKilometro(kmRecorridos, litrosCargados)) {

                List<String> nombreDeresponsables = choferRepository.obtenerNombreDeChofersDeVehiculo(vehiculo.getId());
                VehiculoResponseDTO vehiculoResponseDTO = vehiculoMapper.toVehiculoResponseDTO(vehiculo);

                InconsistenciasKMconCombustiblesResponseDTO inconsistencia = InconsistenciasKMconCombustiblesResponseDTO
                        .builder()
                        .litrosCargados(litrosCargados)
                        .kilometrajeRecorrido(kmRecorridos)
                        .nombresDeResponsables(nombreDeresponsables)
                        .vehiculo(vehiculoResponseDTO)
                        .litrosInconsistente(litrosCargados - kmRecorridos)
                        .build();
                inconsistencias.add(inconsistencia);
            }
        }

        return inconsistencias;
    }


    private double calcularKmDeDispositivoEntreFechas(String unicoIdDeDispositivo, OffsetDateTime from, OffsetDateTime to) {
        Integer deviceId = obtenerdeviceIdByUniqueId(unicoIdDeDispositivo);
        return getKilometros(deviceId, from, to).getDistance();
    }

    @Override
    public KilometrosResponseDTO getKilometros(Integer deviceId, OffsetDateTime from, OffsetDateTime to) {
        return traccarCliente.getKilometros(deviceId, from, to);
    }

    @Override
    public List<PosicionResponseDTO> obtenerPosicionesEnVivo(String uniqueId) {
        OffsetDateTime fromActual = OffsetDateTime.now(ZoneOffset.UTC).minusMinutes(1);
        OffsetDateTime toHardcodeado = OffsetDateTime.parse("2030-12-31T23:59:59Z");
        Integer idDevice = obtenerdeviceIdByUniqueId(uniqueId);
        return posicionMapper.mapPosicionesRequestToPosicionesResponseDTO(traccarCliente.getPosicionesDispositivoTraccar(idDevice, fromActual, toHardcodeado));
    }

    @Override
    public List<PosicionResponseDTO> obtenerPosicionesEnRangoDeFechas(String uniqueId, OffsetDateTime from, OffsetDateTime to) {
        Integer idDevice = obtenerdeviceIdByUniqueId(uniqueId);
        return posicionMapper.mapPosicionesRequestToPosicionesResponseDTO(traccarCliente.getPosicionesDispositivoTraccar(idDevice, from, to));
    }

    private Integer obtenerdeviceIdByUniqueId(String uniqueId) {
        return traccarCliente.obtenerDispositivoByUniqueId(uniqueId).getId();
    }

    private boolean calculoDeCombustiblePorKilometro(double kilometrajeRecorrido, double combustibleCargado) {
        int kmPorLitro = 1;
        return kilometrajeRecorrido < combustibleCargado * kmPorLitro;
    }


}
