package com.gifa_api.service;

import com.gifa_api.dto.VehiculoDTO;
import com.gifa_api.enums.EstadoDeHabilitacion;
import com.gifa_api.model.Chofer;
import com.gifa_api.model.Tarjeta;
import com.gifa_api.model.Vehiculo;
import com.gifa_api.repository.ChoferRepository;
import com.gifa_api.repository.VehiculoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VehiculoServiceimpl implements IVehiculoService {
    final ChoferRepository choferRepository;
   final VehiculoRepository vehiculoRepository;


    @Override
    public void registrarVehiculo(VehiculoDTO vehiculoDTO) throws Exception {

        Optional<Chofer> chofer = choferRepository.findById(vehiculoDTO.getChoferId());
        if (!chofer.isPresent()) {
            throw new Exception();
        }
        Vehiculo vehiculo = Vehiculo
                .builder()
                .patente(vehiculoDTO.getPatente())
                .chasis(vehiculoDTO.getChasis())
                .antiguedad(vehiculoDTO.getAntiguedad())
                .kilometraje(vehiculoDTO.getKilometraje())
                .litrosDeTanque(0)
                .modelo(vehiculoDTO.getModelo())
                .estadoDeHabilitacion(EstadoDeHabilitacion.HABILITADO)
                .chofer(chofer.get())
                .tarjeta(Tarjeta.builder().build())
                .build();


        vehiculoRepository.save(vehiculo);

    }
}
