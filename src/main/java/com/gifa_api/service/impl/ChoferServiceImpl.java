package com.gifa_api.service.impl;

import com.gifa_api.dto.chofer.AsignarChoferDTO;
import com.gifa_api.dto.chofer.ChoferRegistroDTO;
import com.gifa_api.dto.chofer.ChoferResponseDTO;
import com.gifa_api.exception.NotFoundException;
import com.gifa_api.model.Chofer;
import com.gifa_api.model.Usuario;
import com.gifa_api.model.Vehiculo;
import com.gifa_api.repository.IChoferRepository;
import com.gifa_api.repository.IVehiculoRepository;
import com.gifa_api.service.IChoferService;
import com.gifa_api.utils.enums.EstadoChofer;
import com.gifa_api.utils.enums.Rol;
import com.gifa_api.utils.mappers.ChoferMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChoferServiceImpl implements IChoferService {
    private final IVehiculoRepository vehiculoRepository;
    private final IChoferRepository choferRepository;
    private final ChoferMapper choferMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void registro(ChoferRegistroDTO choferRegistroDTO) {
        Usuario usuario = Usuario.builder()
                .usuario(choferRegistroDTO.getUsername())
                .contrasena(passwordEncoder.encode(choferRegistroDTO.getPassword()))
                .rol(Rol.CHOFER)
                .build();

        Chofer chofer = Chofer.builder()
                .usuario(usuario)
                .estadoChofer(EstadoChofer.HABILITADO)
                .nombre(choferRegistroDTO.getNombre())
                .build();

        choferRepository.save(chofer);
    }

    @Override
    public void asignarVehiculo(AsignarChoferDTO asignarChoferDTO) {
        Vehiculo vehiculo = vehiculoRepository.findById(asignarChoferDTO.getIdVehiculo())
                .orElseThrow(() -> new NotFoundException("No se encontró el vehiculo para el chofer del id " + asignarChoferDTO.getIdVehiculo() ));
        Chofer chofer = choferRepository.findById(asignarChoferDTO.getIdChofer())
                .orElseThrow(() -> new NotFoundException("No se encontró el chofer del id " + asignarChoferDTO.getIdChofer() ));

        chofer.setVehiculo(vehiculo);
        choferRepository.save(chofer);
    }

    @Override
    public void habilitar(Integer idChofer) {
        Chofer chofer = choferRepository.findById(idChofer)
                .orElseThrow(() -> new NotFoundException("No se encontró el chofer del id " + idChofer));
        if(chofer.getEstadoChofer().equals(EstadoChofer.INHABILITADO)) {
            chofer.setEstadoChofer(EstadoChofer.HABILITADO);
        }
        choferRepository.save(chofer);
    }

    @Override
    public void inhabilitar(Integer idChofer) {
        Chofer chofer = choferRepository.findByIdWithVehiculo(idChofer)
                .orElseThrow(() -> new NotFoundException("No se encontró el chofer del id " + idChofer));
        if(chofer.getEstadoChofer().equals(EstadoChofer.HABILITADO)) {
            if(chofer.getVehiculo() != null) {
                Vehiculo vehiculo = chofer.getVehiculo();
                vehiculo.setChofers(vehiculo.getChofers().stream().filter(c -> !Objects.equals(c.getId(), idChofer)).collect(Collectors.toSet()));
                vehiculoRepository.save(vehiculo);
            }
            chofer.setEstadoChofer(EstadoChofer.INHABILITADO);
            chofer.setVehiculo(null);
            choferRepository.save(chofer);
        }
    }

    @Override
    public List<ChoferResponseDTO> obtenerAll() {
        return choferMapper.obtenerListaChoferDTO(choferRepository.findAll());
    }
}
