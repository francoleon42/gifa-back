package com.gifa_api.testUnitario;

import com.gifa_api.dto.gestionDeCombustilble.CargaCombustibleRequestDTO;
import com.gifa_api.exception.NotFoundException;
import com.gifa_api.model.CargaCombustible;
import com.gifa_api.model.Tarjeta;
import com.gifa_api.repository.ICargaCombustibleRepository;
import com.gifa_api.repository.ITarjetaRepository;
import com.gifa_api.service.impl.CargaCombustibleServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CargaCombustibleServiceImplTest {

    @Mock
    private ITarjetaRepository tarjetaRepository;

    @Mock
    private ICargaCombustibleRepository cargaCombustibleRepository;

    @InjectMocks
    private CargaCombustibleServiceImpl cargaCombustibleService;

    public CargaCombustibleServiceImplTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void cargarCombustible_debeLanzarNotFoundExceptionCuandoTarjetaNoExiste() {
        CargaCombustibleRequestDTO requestDTO = CargaCombustibleRequestDTO.builder()
                .numeroTarjeta(12345678)
                .cantidadLitros(50)
                .FechaYhora(LocalDateTime.now())
                .build();

        when(tarjetaRepository.findById(requestDTO.getNumeroTarjeta())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NotFoundException.class, () -> cargaCombustibleService.cargarCombustible(requestDTO));

        // Verifica que no se haya intentado guardar la carga de combustible
        verify(cargaCombustibleRepository, never()).save(any(CargaCombustible.class));
    }

    @Test
    void cargarCombustible_debeGuardarCargaCombustibleCuandoTarjetaExiste() {
        // Arrange
        CargaCombustibleRequestDTO requestDTO = CargaCombustibleRequestDTO.builder()
                .numeroTarjeta(12345678)
                .cantidadLitros(50)
                .FechaYhora(LocalDateTime.now())
                .build();

        Tarjeta tarjeta = new Tarjeta();
        when(tarjetaRepository.findById(requestDTO.getNumeroTarjeta())).thenReturn(Optional.of(tarjeta));

        // Act
        cargaCombustibleService.cargarCombustible(requestDTO);

        // Assert
        verify(tarjetaRepository, times(1)).findById(requestDTO.getNumeroTarjeta());
        verify(cargaCombustibleRepository, times(1)).save(any(CargaCombustible.class));
    }
}
