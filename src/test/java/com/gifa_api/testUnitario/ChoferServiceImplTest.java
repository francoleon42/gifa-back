package com.gifa_api.testUnitario;

import com.gifa_api.dto.chofer.ChoferEditDTO;
import com.gifa_api.dto.chofer.ChoferRegistroDTO;
import com.gifa_api.exception.NotFoundException;
import com.gifa_api.model.Chofer;
import com.gifa_api.model.Vehiculo;
import com.gifa_api.repository.IChoferRepository;
import com.gifa_api.repository.IVehiculoRepository;
import com.gifa_api.service.impl.ChoferServiceImpl;
import com.gifa_api.utils.enums.EstadoChofer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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

    @InjectMocks
    private ChoferServiceImpl choferService;



    @Test
    void registrarChofer_debeGuardarChoferSiVehiculoExiste() {
        // Arrange
        ChoferRegistroDTO choferRegistroDTO = new ChoferRegistroDTO();
        choferRegistroDTO.setNombre("Juan Pérez");
        choferRegistroDTO.setId_vehiculo(1);

        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setId(1);

        when(vehiculoRepository.findById(1)).thenReturn(Optional.of(vehiculo));

        // Act
        choferService.registro(choferRegistroDTO);

        // Assert
        verify(choferRepository, times(1)).save(any(Chofer.class));
    }

    @Test
    void registrarChofer_debeLanzarExcepcionSiVehiculoNoExiste() {
        // Arrange
        ChoferRegistroDTO choferRegistroDTO = new ChoferRegistroDTO();
        choferRegistroDTO.setNombre("Juan Pérez");
        choferRegistroDTO.setId_vehiculo(1);

        when(vehiculoRepository.findById(1)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NotFoundException.class, () -> choferService.registro(choferRegistroDTO));
        verify(choferRepository, never()).save(any(Chofer.class));
    }

    @Test
    void habilitarChofer_debeHabilitarSiEstaInhabilitado() {
        // Arrange
        ChoferEditDTO choferEditDTO = new ChoferEditDTO();
        choferEditDTO.setId_chofer(1);

        Chofer chofer = new Chofer();
        chofer.setId(1);
        chofer.setEstadoChofer(EstadoChofer.INHABILITADO);

        when(choferRepository.findById(1)).thenReturn(Optional.of(chofer));

        // Act
        choferService.habilitar(choferEditDTO);

        // Assert
        verify(choferRepository, times(1)).save(any(Chofer.class));
        assertEquals(EstadoChofer.HABILITADO, chofer.getEstadoChofer());
    }

    @Test
    void habilitarChofer_debeLanzarExcepcionSiNoExiste() {
        // Arrange
        ChoferEditDTO choferEditDTO = new ChoferEditDTO();
        choferEditDTO.setId_chofer(1);

        when(choferRepository.findById(1)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NotFoundException.class, () -> choferService.habilitar(choferEditDTO));
        verify(choferRepository, never()).save(any(Chofer.class));
    }

    @Test
    void inhabilitarChofer_debeInhabilitarSiEstaHabilitado() {
        // Arrange
        ChoferEditDTO choferEditDTO = new ChoferEditDTO();
        choferEditDTO.setId_chofer(1);

        Chofer chofer = new Chofer();
        chofer.setId(1);
        chofer.setEstadoChofer(EstadoChofer.HABILITADO);

        when(choferRepository.findById(1)).thenReturn(Optional.of(chofer));

        // Act
        choferService.inhabilitar(choferEditDTO);

        // Assert
        verify(choferRepository, times(1)).save(any(Chofer.class));
        assertEquals(EstadoChofer.INHABILITADO, chofer.getEstadoChofer());
    }

    @Test
    void inhabilitarChofer_debeLanzarExcepcionSiNoExiste() {
        // Arrange
        ChoferEditDTO choferEditDTO = new ChoferEditDTO();
        choferEditDTO.setId_chofer(1);

        when(choferRepository.findById(1)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NotFoundException.class, () -> choferService.inhabilitar(choferEditDTO));
        verify(choferRepository, never()).save(any(Chofer.class));
    }

}
