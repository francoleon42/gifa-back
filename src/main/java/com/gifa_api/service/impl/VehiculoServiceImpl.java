package com.gifa_api.service.impl;

import com.gifa_api.dto.RegistarVehiculoDTO;
import com.gifa_api.enums.EstadoDeHabilitacion;
import com.gifa_api.exception.NotFoundException;
import com.gifa_api.model.Tarjeta;
import com.gifa_api.model.Vehiculo;
import com.gifa_api.repository.VehiculoRepository;
import com.gifa_api.service.IVehiculoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VehiculoServiceImpl implements IVehiculoService {
    private final VehiculoRepository vehiculoRepository;


    @Override
    public List<Vehiculo> getVehiculos() {
        return vehiculoRepository.findAll();
    }

    @Override
    public void registrar(RegistarVehiculoDTO vehiculoDTO) throws Exception {

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
    public void inhabilitar(Integer vehiculoId) throws Exception {
        Vehiculo vehiculo = vehiculoRepository.findById(vehiculoId)
                .orElseThrow(() -> new NotFoundException("No se encontró el vehiculo con id: " + vehiculoId));

        vehiculo.inhabilitar();
        vehiculoRepository.save(vehiculo);
    }

    @Override
    public void habilitar(Integer vehiculoId) throws Exception {
        Vehiculo vehiculo = vehiculoRepository.findById(vehiculoId)
                .orElseThrow(() -> new NotFoundException("No se encontró el vehiculo con id: " + vehiculoId));

        vehiculo.habilitar();
        vehiculoRepository.save(vehiculo);
    }
}
