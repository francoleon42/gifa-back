package com.gifa_api.service.impl;

import com.gifa_api.dto.chofer.AsignarChoferDTO;
import com.gifa_api.dto.chofer.ChoferEditDTO;
import com.gifa_api.dto.chofer.ChoferRegistroDTO;
import com.gifa_api.dto.chofer.ChoferResponseDTO;
import com.gifa_api.exception.NotFoundException;
import com.gifa_api.model.Chofer;
import com.gifa_api.model.GestorDePedidos;
import com.gifa_api.model.Vehiculo;
import com.gifa_api.repository.IChoferRepository;
import com.gifa_api.repository.IVehiculoRepository;
import com.gifa_api.service.IChoferService;
import com.gifa_api.utils.enums.EstadoChofer;
import com.gifa_api.utils.mappers.ChoferMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChoferServiceImpl implements IChoferService {
    private final IVehiculoRepository vehiculoRepository;
    private final IChoferRepository choferRepository;
    private final ChoferMapper choferMapper;

    @Override
    public void registro(ChoferRegistroDTO choferRegistroDTO) {


        Chofer chofer = Chofer
                .builder()
                .estadoChofer(EstadoChofer.HABILITADO)
                .nombre(choferRegistroDTO.getNombre())
                .build();

        choferRepository.save(chofer);
    }


    @Override
    public void asignarVehiculo(AsignarChoferDTO asignarChoferDTO ) {
        Vehiculo vehiculo = vehiculoRepository.findById(asignarChoferDTO.getIdVehiculo())
                .orElseThrow(() -> new NotFoundException("No se encontró el vehiculo para el chofer del id " + asignarChoferDTO.getIdVehiculo() ));
        Chofer chofer = choferRepository.findById(asignarChoferDTO.getIdChofer())
                .orElseThrow(() -> new NotFoundException("No se encontró el chofer del id " + asignarChoferDTO.getIdChofer() ));

        chofer.setVehiculo(vehiculo);
        choferRepository.save(chofer);
    }
    @Override
    public void habilitar(ChoferEditDTO choferEditDTO) {
        Chofer chofer = choferRepository.findById(choferEditDTO.getId_chofer())
                .orElseThrow(() -> new NotFoundException("No se encontró el chofer del id " + choferEditDTO.getId_chofer()));
        if(chofer.getEstadoChofer().equals(EstadoChofer.INHABILITADO)) {
            chofer.setEstadoChofer(EstadoChofer.HABILITADO);
        }
        choferRepository.save(chofer);
    }
    @Override
    public void inhabilitar(ChoferEditDTO choferEditDTO) {
        Chofer chofer = choferRepository.findById(choferEditDTO.getId_chofer())
                .orElseThrow(() -> new NotFoundException("No se encontró el chofer del id " + choferEditDTO.getId_chofer()));
        if(chofer.getEstadoChofer().equals(EstadoChofer.HABILITADO)) {
            chofer.setEstadoChofer(EstadoChofer.INHABILITADO);
        }
        choferRepository.save(chofer);
    }

    @Override
    public List<ChoferResponseDTO> obtenerAll() {
        return choferMapper.obtenerListaChoferDTO(choferRepository.findAll());
    }
}
