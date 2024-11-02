package com.gifa_api.service.impl;

import com.gifa_api.client.ITraccarCliente;
import com.gifa_api.dto.traccar.CrearDispositivoRequestDTO;
import com.gifa_api.dto.traccar.InconsistenciasKMconCombustiblesResponseDTO;
import com.gifa_api.dto.traccar.ObtenerDispositivoRequestDTO;
import com.gifa_api.dto.vehiculo.VehiculoResponseDTO;
import com.gifa_api.model.Vehiculo;
import com.gifa_api.repository.IChoferRepository;
import com.gifa_api.repository.IDispositivoRepository;
import com.gifa_api.repository.IVehiculoRepository;
import com.gifa_api.service.ICargaCombustibleService;
import com.gifa_api.service.IDispositivoService;
import com.gifa_api.service.ITraccarService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TraccarServiceImpl implements ITraccarService {

    private final ITraccarCliente traccarCliente;
    private final ICargaCombustibleService cargaCombustibleService;
    private final IDispositivoService dispositivoService;
    private final IVehiculoRepository vehiculoRepository;
    private final IChoferRepository choferRepository;

    private final IDispositivoRepository dispositivoRepository;

    @Override
    public void crearDispositivo(CrearDispositivoRequestDTO crearDispositivoRequestDTO) {
        traccarCliente.postCrearDispositivoTraccar(crearDispositivoRequestDTO);
    }

    @Override
    public List<ObtenerDispositivoRequestDTO> obtenerDispositivos() {
        return traccarCliente.getDispositivos();
    }

    @Override
    public List<InconsistenciasKMconCombustiblesResponseDTO> getInconsistencias(OffsetDateTime fecha) {
        List<InconsistenciasKMconCombustiblesResponseDTO> inconsistencias = new ArrayList<>();
        for (Vehiculo vehiculo : vehiculoRepository.findAll()) {
            int kmRecorridos = dispositivoService.calcularKmDeDispositivoDespuesDeFecha(vehiculo.getDispositivo().getUnicoId(), fecha);
            double litrosCargados = cargaCombustibleService.combustibleCargadoEn(vehiculo.getTarjeta().getNumero(), fecha);

            if (calculoDeCombustiblePorKilometro(kmRecorridos, litrosCargados)) {

                List<String> nombreDeresponsables = choferRepository.obtenerNombreDeChofersDeVehiculo(vehiculo.getId());
                VehiculoResponseDTO vehiculoResponseDTO = VehiculoResponseDTO
                        .builder()
                        .modelo(vehiculo.getModelo())
                        .antiguedad(vehiculo.getAntiguedad())
                        .estadoVehiculo(vehiculo.getEstadoVehiculo())
                        .estadoDeHabilitacion(vehiculo.getEstadoDeHabilitacion())
                        .fechaVencimiento(vehiculo.getFechaVencimiento())
                        .kilometraje(vehiculo.getKilometraje())
                        .patente(vehiculo.getPatente())
                        .build();

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

    private boolean calculoDeCombustiblePorKilometro(int kilometrajeRecorrido, double combustibleCargado) {
        int kmPorLitro = 10;
        return kilometrajeRecorrido < combustibleCargado * kmPorLitro;
    }

}
