package com.gifa_api.service.impl;

import com.gifa_api.exception.NotFoundException;
import com.gifa_api.model.KilometrajeVehiculo;
import com.gifa_api.model.Vehiculo;
import com.gifa_api.repository.IKilometrajeVehiculoRepository;
import com.gifa_api.repository.IVehiculoRepository;
import com.gifa_api.service.IKilometrajeVehiculoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
public class KilometrajeVehiculoServiceImpl implements IKilometrajeVehiculoService {
    private final IKilometrajeVehiculoRepository kilometrajeVehiculoRepository;
    private final IVehiculoRepository vehiculoRepository;

    @Override
    public void addKilometrajeVehiculo(Integer kilometraje, OffsetDateTime fecha, Integer idVehiculo) {
        Vehiculo vehiculo = vehiculoRepository.findById(idVehiculo)
                .orElseThrow(() -> new NotFoundException("No se encontró el vehiculo para KilometrajeVehiculo " + idVehiculo));
        KilometrajeVehiculo kilometrajeVehiculo = KilometrajeVehiculo
                .builder()
                .kilometrosRecorridos(kilometraje)
                .fecha(fecha)
                .vehiculo(vehiculo)
                .build();

        kilometrajeVehiculoRepository.save(kilometrajeVehiculo);
    }
}
