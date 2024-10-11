package com.gifa_api.service.impl;

import com.gifa_api.dto.chofer.ChoferEditDTO;
import com.gifa_api.dto.chofer.ChoferRegistroDTO;
import com.gifa_api.exception.NotFoundException;
import com.gifa_api.model.Chofer;
import com.gifa_api.model.GestorDePedidos;
import com.gifa_api.model.Vehiculo;
import com.gifa_api.repository.IChoferRepository;
import com.gifa_api.repository.IVehiculoRepository;
import com.gifa_api.service.IChoferService;
import com.gifa_api.utils.enums.EstadoChofer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChoferServiceImpl implements IChoferService {
    private final IVehiculoRepository vehiculoRepository;
    private final IChoferRepository choferRepository;

    @Override
    public void registro(ChoferRegistroDTO choferRegistroDTO) {
        Vehiculo vehiculo = vehiculoRepository.findById(choferRegistroDTO.getId_vehiculo())
                .orElseThrow(() -> new NotFoundException("No se encontró el vehiculo para el chofer del id " + choferRegistroDTO.getId_vehiculo() ));

        Chofer chofer = Chofer
                .builder()
                .estadoChofer(EstadoChofer.HABILITADO)
                .nombre(choferRegistroDTO.getNombre())
                .vehiculo(vehiculo)
                .build();

        choferRepository.save(chofer);
    }

    @Override
    public void habilitar(ChoferEditDTO choferEditDTO) {
        Chofer chofer = choferRepository.findById(choferEditDTO.getId_chofer())
                .orElseThrow(() -> new NotFoundException("No se encontró el chofer del id " + choferEditDTO.getId_chofer()));
        if(chofer.getEstadoChofer().equals(EstadoChofer.INHABILITADO)) {
            chofer.setEstadoChofer(EstadoChofer.HABILITADO);
        }
    }
    @Override
    public void inhabilitar(ChoferEditDTO choferEditDTO) {
        Chofer chofer = choferRepository.findById(choferEditDTO.getId_chofer())
                .orElseThrow(() -> new NotFoundException("No se encontró el chofer del id " + choferEditDTO.getId_chofer()));
        if(chofer.getEstadoChofer().equals(EstadoChofer.HABILITADO)) {
            chofer.setEstadoChofer(EstadoChofer.INHABILITADO);
        }
    }
}
