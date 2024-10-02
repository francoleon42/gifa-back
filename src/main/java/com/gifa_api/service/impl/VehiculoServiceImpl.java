package com.gifa_api.service.impl;

import com.gifa_api.dto.vehiculo.AsignarParteRequestDTO;
import com.gifa_api.dto.vehiculo.RegistarVehiculoDTO;
import com.gifa_api.exception.NotFoundException;
import com.gifa_api.model.ItemDeInventario;
import com.gifa_api.model.ParteDeVehiculo;
import com.gifa_api.model.Tarjeta;
import com.gifa_api.model.Vehiculo;
import com.gifa_api.repository.IParteDeVehiculoRepository;
import com.gifa_api.repository.IVehiculoRepository;
import com.gifa_api.repository.ItemDeInventarioRepository;
import com.gifa_api.service.IItemDeIventarioService;
import com.gifa_api.service.IVehiculoService;
import com.gifa_api.utils.enums.EstadoDeHabilitacion;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VehiculoServiceImpl implements IVehiculoService {
    private final IVehiculoRepository vehiculoRepository;
    private final IParteDeVehiculoRepository parteDeVehiculoRepository;
    private final ItemDeInventarioRepository itemDeInventarioRepository;


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

    @Override
    public void asignarParte(AsignarParteRequestDTO asignarParteRequestDTO) {
        Vehiculo vehiculo = vehiculoRepository.findById(asignarParteRequestDTO.getVehiculoId())
                .orElseThrow(() -> new NotFoundException("No se encontró el vehiculo con id: " + asignarParteRequestDTO.getVehiculoId()));

        ItemDeInventario item = itemDeInventarioRepository.findById(asignarParteRequestDTO.getParteId())
                .orElseThrow(() -> new NotFoundException("No se encontró el item con id: " + asignarParteRequestDTO.getVehiculoId()));

        ParteDeVehiculo parteDeVehiculo = ParteDeVehiculo.builder()
                .fechaVencimiento(asignarParteRequestDTO.getFecha())
                .vehiculo(vehiculo)
                .itemDeInventario(item)
                .build();

        parteDeVehiculoRepository.save(parteDeVehiculo);
    }
}
