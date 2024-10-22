package com.gifa_api.service.impl;

import com.gifa_api.dto.mantenimiento.RegistrarMantenimientoDTO;
import com.gifa_api.dto.vehiculo.ListaVehiculosResponseDTO;
import com.gifa_api.dto.vehiculo.RegistarVehiculoDTO;
import com.gifa_api.exception.NotFoundException;
import com.gifa_api.model.Tarjeta;
import com.gifa_api.model.Vehiculo;
import com.gifa_api.repository.ITarjetaRepository;
import com.gifa_api.repository.IVehiculoRepository;
import com.gifa_api.repository.ItemDeInventarioRepository;
import com.gifa_api.service.IMantenimientoService;
import com.gifa_api.service.IVehiculoService;
import com.gifa_api.utils.enums.EstadoDeHabilitacion;
import com.gifa_api.utils.enums.EstadoVehiculo;
import com.gifa_api.utils.mappers.VehiculoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class VehiculoServiceImpl implements IVehiculoService {
    private final IVehiculoRepository vehiculoRepository;
    private final IMantenimientoService iMantenimientoService;
    private final ITarjetaRepository tarjetaRepository;
    private final VehiculoMapper vehiculoMapper;


    @Override
    public ListaVehiculosResponseDTO getVehiculos() {
        return vehiculoMapper.toListaVehiculosResponseDTO(vehiculoRepository.findAll());
    }

    @Override
    public void registrar(RegistarVehiculoDTO vehiculoDTO) {
        Tarjeta tarjeta = Tarjeta.builder()
                .numero(generarNumeroTarjeta())
                .build();
        tarjetaRepository.save(tarjeta);

        Vehiculo vehiculo = Vehiculo
                .builder()
                .patente(vehiculoDTO.getPatente())
                .antiguedad(vehiculoDTO.getAntiguedad())
                .kilometraje(vehiculoDTO.getKilometraje())
                .modelo(vehiculoDTO.getModelo())
                .estadoDeHabilitacion(EstadoDeHabilitacion.HABILITADO)
                .estadoVehiculo(EstadoVehiculo.REPARADO)
                .fechaVencimiento(vehiculoDTO.getFechaRevision())
                .tarjeta(tarjeta)
                .build();

        vehiculoRepository.save(vehiculo);
    }

    private Integer generarNumeroTarjeta() {
        return new Random().nextInt(90000000) + 10000000;
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
