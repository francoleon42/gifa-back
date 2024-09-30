package com.gifa_api.service.impl;

import com.gifa_api.dto.RegistarVehiculoDTO;
import com.gifa_api.enums.EstadoDeHabilitacion;
import com.gifa_api.model.Chofer;
import com.gifa_api.model.Tarjeta;
import com.gifa_api.model.Vehiculo;
import com.gifa_api.repository.ChoferRepository;
import com.gifa_api.repository.VehiculoRepository;
import com.gifa_api.service.IVehiculoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VehiculoServiceimpl implements IVehiculoService {
    final ChoferRepository choferRepository;
   final VehiculoRepository vehiculoRepository;


    @Override
    public void registrar(RegistarVehiculoDTO vehiculoDTO) throws Exception {

        Optional<Chofer> chofer = choferRepository.findById(vehiculoDTO.getChoferId());
        if (!chofer.isPresent()) {
            throw new Exception();
        }
        Vehiculo vehiculo = Vehiculo
                .builder()
                .patente(vehiculoDTO.getPatente())
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

    @Override
    public void inhabilitar(Integer idVehiculoToInhabilitar) throws Exception {
        Optional<Vehiculo> vehiculo = vehiculoRepository.findById(idVehiculoToInhabilitar);
        if (!vehiculo.isPresent()) {
            throw new Exception();
        }
        vehiculo.get().inhabilitar();
        vehiculoRepository.save(vehiculo.get());
    }
    @Override
    public void habilitar(Integer idVehiculoToInhabilitar) throws Exception {
        Optional<Vehiculo> vehiculo = vehiculoRepository.findById(idVehiculoToInhabilitar);
        if (!vehiculo.isPresent()) {
            throw new Exception();
        }
        vehiculo.get().habilitar();
        vehiculoRepository.save(vehiculo.get());
    }
}
