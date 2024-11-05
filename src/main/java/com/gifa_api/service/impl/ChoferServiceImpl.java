package com.gifa_api.service.impl;

import com.gifa_api.dto.chofer.AsignarChoferDTO;
import com.gifa_api.dto.chofer.ChoferRegistroDTO;
import com.gifa_api.dto.chofer.ChoferResponseDTO;
import com.gifa_api.exception.BadRequestException;
import com.gifa_api.exception.NotFoundException;
import com.gifa_api.model.Chofer;
import com.gifa_api.model.Usuario;
import com.gifa_api.model.Vehiculo;
import com.gifa_api.repository.IChoferRepository;
import com.gifa_api.repository.IVehiculoRepository;
import com.gifa_api.service.IChoferService;
import com.gifa_api.utils.enums.Rol;
import com.gifa_api.utils.mappers.ChoferMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChoferServiceImpl implements IChoferService {
    private final IVehiculoRepository vehiculoRepository;
    private final IChoferRepository choferRepository;
    private final ChoferMapper choferMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void registro(ChoferRegistroDTO choferRegistroDTO) {
        // Validar el DTO
        validarChoferRegistroDTO(choferRegistroDTO);
        Usuario usuario = Usuario.builder()
                .usuario(choferRegistroDTO.getUsername())
                .contrasena(passwordEncoder.encode(choferRegistroDTO.getPassword()))
                .rol(Rol.CHOFER)
                .build();

        Chofer chofer = Chofer.builder()
                .usuario(usuario)
                .nombre(choferRegistroDTO.getNombre())
                .build();

        choferRepository.save(chofer);
    }

    @Override
    public void asignarVehiculo(AsignarChoferDTO asignarChoferDTO) {
        Vehiculo vehiculo = vehiculoRepository.findById(asignarChoferDTO.getIdVehiculo())
                .orElseThrow(() -> new NotFoundException("No se encontró el vehiculo para el chofer del id " + asignarChoferDTO.getIdVehiculo()));
        Chofer chofer = choferRepository.findById(asignarChoferDTO.getIdChofer())
                .orElseThrow(() -> new NotFoundException("No se encontró el chofer del id " + asignarChoferDTO.getIdChofer()));

        chofer.addVehiculo(vehiculo);
        choferRepository.save(chofer);
    }



    @Override
    public void inhabilitarUsuarioChofer(Integer idUsuario) {
        Chofer chofer = choferRepository.findByUsuario_Id(idUsuario)
                .orElseThrow(() -> new NotFoundException("No se encontró el usuario del id " + idUsuario));

        designarVehiculosDeChoferInhabilitado(chofer);
        chofer.desvincularVehiculo();
        choferRepository.save(chofer);

    }

    private void designarVehiculosDeChoferInhabilitado(Chofer chofer) {
        if (chofer.getVehiculo() != null) {
            Vehiculo vehiculo = chofer.getVehiculo();
            vehiculo.removerChofer(chofer);
            vehiculoRepository.save(vehiculo);
        }
    }


    @Override
    public List<ChoferResponseDTO> obtenerAll() {
        return choferMapper.obtenerListaChoferDTO(choferRepository.findAll());
    }

    private void validarChoferRegistroDTO(ChoferRegistroDTO choferRegistroDTO) {
        if (choferRegistroDTO.getUsername() == null || choferRegistroDTO.getUsername().trim().isEmpty()) {
            throw new BadRequestException("El nombre de usuario no puede estar vacío.");
        }
        if (!choferRegistroDTO.getUsername().matches("^[a-zA-Z0-9]*$")) {
            throw new BadRequestException("El nombre de usuario no debe contener caracteres especiales.");
        }
        if (choferRegistroDTO.getPassword() == null || choferRegistroDTO.getPassword().trim().isEmpty()) {
            throw new BadRequestException("La contraseña no puede estar vacía.");
        }
        if (choferRegistroDTO.getNombre() == null || choferRegistroDTO.getNombre().trim().isEmpty()) {
            throw new BadRequestException("El nombre del chofer no puede estar vacío.");
        }
    }
}
