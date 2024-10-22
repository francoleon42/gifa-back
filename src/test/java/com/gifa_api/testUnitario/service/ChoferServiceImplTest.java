package com.gifa_api.testUnitario.service;

import com.gifa_api.dto.chofer.AsignarChoferDTO;
import com.gifa_api.dto.chofer.ChoferRegistroDTO;
import com.gifa_api.exception.NotFoundException;
import com.gifa_api.model.Chofer;
import com.gifa_api.model.Vehiculo;
import com.gifa_api.repository.IChoferRepository;
import com.gifa_api.repository.IVehiculoRepository;
import com.gifa_api.service.IChoferService;
import com.gifa_api.service.impl.ChoferServiceImpl;
import com.gifa_api.utils.enums.EstadoChofer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)

class ChoferServiceImplTest {

    @Mock
    private IVehiculoRepository vehiculoRepository;

    @Mock
    private IChoferRepository choferRepository;

    @Mock
    private  PasswordEncoder passwordEncoder;

    @InjectMocks
    private ChoferServiceImpl choferService;


    @Test
    void habilitarChofer_debeLanzarExcepcionSiNoExiste() {
        // Arrange

        when(choferRepository.findById(1)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NotFoundException.class, () -> choferService.habilitar(1));
        verify(choferRepository, never()).save(any(Chofer.class));
    }


    @Test
    void inhabilitarChofer_debeLanzarExcepcionSiNoExiste() {
        // Arrange
        when(choferRepository.findById(1)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NotFoundException.class, () -> choferService.inhabilitar(1));
        verify(choferRepository, never()).save(any(Chofer.class));
    }

    @Test

    void asignarVehiculo_NoSeEncuentraVehiculoParaElChofer(){
        AsignarChoferDTO asignarChoferDTO = new AsignarChoferDTO(1,1);


        when(vehiculoRepository.findById(asignarChoferDTO.getIdVehiculo())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class,() -> choferService.asignarVehiculo(asignarChoferDTO));

        verify(choferRepository,never()).save(any(Chofer.class));
        verify(vehiculoRepository,times(1)).findById(asignarChoferDTO.getIdVehiculo());
    }

    @Test
    void asignarVehiculo_NoSeEncuentraElChofer(){
        AsignarChoferDTO asignarChoferDTO = new AsignarChoferDTO(1,1);

        when(vehiculoRepository.findById(asignarChoferDTO.getIdVehiculo())).thenReturn(Optional.of(new Vehiculo()));
        when(choferRepository.findById(asignarChoferDTO.getIdChofer())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class,() -> choferService.asignarVehiculo(asignarChoferDTO));

        verify(vehiculoRepository,times(1)).findById(asignarChoferDTO.getIdVehiculo());
        verify(choferRepository,times(1)).findById(asignarChoferDTO.getIdVehiculo());
        verify(choferRepository,never()).save(any(Chofer.class));
    }


    @Test
    void asignarVehiculo(){
        AsignarChoferDTO asigancionChoferDTO = new AsignarChoferDTO(1,1);
        Vehiculo vehiculo = new Vehiculo();
        Chofer chofer = new Chofer();

        when(vehiculoRepository.findById(asigancionChoferDTO.getIdVehiculo())).thenReturn(Optional.of(vehiculo));
        when(choferRepository.findById(asigancionChoferDTO.getIdChofer())).thenReturn(Optional.of(chofer));

        choferService.asignarVehiculo(asigancionChoferDTO);

        verify(vehiculoRepository,times(1)).findById(asigancionChoferDTO.getIdVehiculo());
        verify(choferRepository,times(1)).findById(asigancionChoferDTO.getIdChofer());
        verify(choferRepository,times(1)).save(any(Chofer.class));
        assertEquals(chofer.getVehiculo(),vehiculo);
    }

    @Test
    void registrarChofer_debeGuardarChoferSiVehiculoExiste() {
        // Arrange
        ChoferRegistroDTO choferRegistroDTO = new ChoferRegistroDTO("chofer","1234","CHOFER");

        when(passwordEncoder.encode(choferRegistroDTO.getPassword())).thenReturn("1234");
        // Act
        choferService.registro(choferRegistroDTO);

        // Assert
        verify(choferRepository, times(1)).save(any(Chofer.class));
    }
    @Test
    void habilitarChofer_debeHabilitarSiEstaInhabilitado() {
        // Arrange

        Chofer chofer = new Chofer();
        chofer.setId(1);
        chofer.setEstadoChofer(EstadoChofer.INHABILITADO);

        when(choferRepository.findById(1)).thenReturn(Optional.of(chofer));

        // Act
        choferService.habilitar(1);

        // Assert
        verify(choferRepository, times(1)).save(any(Chofer.class));
        verify(choferRepository, times(1)).findById(1);

        assertEquals(EstadoChofer.HABILITADO, chofer.getEstadoChofer());
    }
    @Test
    void inhabilitarChofer_debeInhabilitarSiEstaHabilitado() {
        // Arrange


        Chofer chofer = new Chofer();
        chofer.setId(1);
        chofer.setEstadoChofer(EstadoChofer.HABILITADO);

        when(choferRepository.findById(1)).thenReturn(Optional.of(chofer));

        // Act
        choferService.inhabilitar(1);

        // Assert

        assertEquals(EstadoChofer.INHABILITADO, chofer.getEstadoChofer());
        verify(choferRepository, times(1)).findById(1);
        verify(choferRepository, times(1)).save(any(Chofer.class));
    }

}
