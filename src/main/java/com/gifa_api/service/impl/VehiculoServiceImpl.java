package com.gifa_api.service.impl;

import com.gifa_api.dto.mantenimiento.RegistrarMantenimientoDTO;
import com.gifa_api.dto.vehiculo.RegistarVehiculoDTO;
import com.gifa_api.exception.NotFoundException;
import com.gifa_api.model.Tarjeta;
import com.gifa_api.model.Vehiculo;
import com.gifa_api.repository.IVehiculoRepository;
import com.gifa_api.repository.ItemDeInventarioRepository;
import com.gifa_api.service.IMantenimientoService;
import com.gifa_api.service.IVehiculoService;
import com.gifa_api.utils.enums.EstadoDeHabilitacion;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VehiculoServiceImpl implements IVehiculoService {
    private final IVehiculoRepository vehiculoRepository;
    private final ItemDeInventarioRepository itemDeInventarioRepository;
    private final IMantenimientoService iMantenimientoService;


    @Override
    public List<Vehiculo> getVehiculos() {
        return vehiculoRepository.findAll();
    }

    @Override
    public void registrar(RegistarVehiculoDTO vehiculoDTO) {

        Vehiculo vehiculo = Vehiculo
                .builder()
                .patente(vehiculoDTO.getPatente())
                .antiguedad(vehiculoDTO.getAntiguedad())
                .kilometraje(vehiculoDTO.getKilometraje())
                .litrosDeTanque(0)
                .modelo(vehiculoDTO.getModelo())
                .estadoDeHabilitacion(EstadoDeHabilitacion.HABILITADO)
                .fechaVencimiento(LocalDate.now().plusMonths(2))
                .tarjeta(Tarjeta.builder().build())
                .build();


        vehiculoRepository.save(vehiculo);
    }

    @Override
    public void inhabilitar(Integer vehiculoId) {
        Vehiculo vehiculo = vehiculoRepository.findById(vehiculoId)
                .orElseThrow(() -> new NotFoundException("No se encontró el vehiculo con id: " + vehiculoId));

        vehiculo.inhabilitar();
        vehiculoRepository.save(vehiculo);
    }

    @Override
    public void habilitar(Integer vehiculoId) {
        Vehiculo vehiculo = vehiculoRepository.findById(vehiculoId)
                .orElseThrow(() -> new NotFoundException("No se encontró el vehiculo con id: " + vehiculoId));

        vehiculo.habilitar();
        vehiculoRepository.save(vehiculo);
    }

    @Scheduled(fixedRate = 86400000)  // Ejecuta cada 24 horas (86400000 milisegundos)
    public void verificarFechaVencimiento() {
        LocalDate hoy = LocalDate.now();
        List<Vehiculo> allVehiculos = vehiculoRepository.findAll();
        for (Vehiculo vehiculo : allVehiculos) {
            if (!vehiculo.getFechaVencimiento().isAfter(hoy)) {
                iMantenimientoService.crearMantenimiento(RegistrarMantenimientoDTO
                        .builder()
                        .vehiculo_id(vehiculo.getId())
                        .asunto("Revision periodica")
                        .build());
            }
        }

    }


}
