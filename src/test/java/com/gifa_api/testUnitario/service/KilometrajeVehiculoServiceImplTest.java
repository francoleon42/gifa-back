package com.gifa_api.testUnitario.service;

import com.gifa_api.exception.NotFoundException;
import com.gifa_api.model.KilometrajeVehiculo;
import com.gifa_api.model.Vehiculo;
import com.gifa_api.repository.IKilometrajeVehiculoRepository;
import com.gifa_api.repository.IVehiculoRepository;
import com.gifa_api.service.impl.KilometrajeVehiculoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class KilometrajeVehiculoServiceImplTest {

    @InjectMocks
    private KilometrajeVehiculoServiceImpl kilometrajeVehiculoService;

    @Mock
    private IKilometrajeVehiculoRepository kilometrajeVehiculoRepository;

    @Mock
    private IVehiculoRepository vehiculoRepository;

    private Vehiculo vehiculo;
    private KilometrajeVehiculo kilometrajeVehiculo;

    @BeforeEach
    void setUp() {

        vehiculo = new Vehiculo();
        vehiculo.setId(1);

        kilometrajeVehiculo = KilometrajeVehiculo.builder()
                .kilometrosRecorridos(100)
                .fecha(OffsetDateTime.now())
                .vehiculo(vehiculo)
                .build();
    }

    @Test
    void addKilometrajeVehiculo_ShouldThrowNotFoundException_WhenVehiculoNotFound() {
        Integer kilometraje = 150;
        OffsetDateTime fecha = OffsetDateTime.now();
        Integer idVehiculo = 1;

        when(vehiculoRepository.findById(idVehiculo)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            kilometrajeVehiculoService.addKilometrajeVehiculo(kilometraje, fecha, idVehiculo);
        });

        assertEquals("No se encontró el vehiculo para KilometrajeVehiculo " + idVehiculo, exception.getMessage());
    }


    @Test
    void addKilometrajeVehiculo_ShouldSaveKilometrajeVehiculo() {
        Integer kilometraje = 150;
        OffsetDateTime fecha = OffsetDateTime.now();
        Integer idVehiculo = 1;

        when(vehiculoRepository.findById(idVehiculo)).thenReturn(Optional.of(vehiculo));
        when(kilometrajeVehiculoRepository.save(any(KilometrajeVehiculo.class))).thenReturn(kilometrajeVehiculo);

        kilometrajeVehiculoService.addKilometrajeVehiculo(kilometraje, fecha, idVehiculo);

        verify(kilometrajeVehiculoRepository).save(any(KilometrajeVehiculo.class));
    }

}
