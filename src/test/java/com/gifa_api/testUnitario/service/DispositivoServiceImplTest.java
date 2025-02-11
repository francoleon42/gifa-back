package com.gifa_api.testUnitario.service;

import com.gifa_api.dto.traccar.CrearDispositivoRequestDTO;
import com.gifa_api.exception.BadRequestException;
import com.gifa_api.exception.NotFoundException;

import com.gifa_api.model.Dispositivo;

import static org.junit.jupiter.api.Assertions.*;

import com.gifa_api.model.Vehiculo;
import com.gifa_api.repository.IDispositivoRepository;
import com.gifa_api.repository.IVehiculoRepository;
import com.gifa_api.service.IKilometrajeVehiculoService;
import com.gifa_api.service.impl.DispositivoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static java.util.Optional.empty;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DispositivoServiceImplTest {
    @Mock
    private IVehiculoRepository vehiculoRepository;

    @Mock
    private IDispositivoRepository dispositivoRepository;


    @Mock
    private IKilometrajeVehiculoService kilometrajeVehiculoService;

    @InjectMocks
    private DispositivoServiceImpl serviceDispositivo;

    private Dispositivo dispositivo;
    private CrearDispositivoRequestDTO dispositivoRequestDTO;
    private Vehiculo vehiculo;

    private final String unicoIdDispositivo = "uniqueId";

    @BeforeEach
    void setUp(){
         dispositivoRequestDTO = CrearDispositivoRequestDTO.builder().name("name").uniqueId("uniqueid").build();
         dispositivo = Dispositivo.builder()
                 .nombre("disp")
                 .unicoId("uniqueId")
                 .id(1)
                 .build();
        vehiculo = Vehiculo.builder().id(1).kilometrajeUsado(1000).build();

    }

    @Test
    void crearDispositivo_nombreNoPuedeSerVacio(){
        dispositivoRequestDTO.setName("");
        verificarNoRegistroDeDispositivo();
    }

    @Test
    void crearDispositivo_uniqueIdNoPuedeSerVacio(){
        dispositivoRequestDTO.setName("");
        verificarNoRegistroDeDispositivo();
    }

    @Test
    void crearDispositivo_nombreNoPuedeSerNull(){
        dispositivoRequestDTO.setName(null);
        verificarNoRegistroDeDispositivo();
    }

    @Test
    void crearDispositivo_uniqueIdNoPuedeSerNull(){
        dispositivoRequestDTO.setName(null);
        verificarNoRegistroDeDispositivo();
    }

    @Test
    public void crearDispositivo_vehiculoInvalidoExcepcion() {
        Integer idVehiculo = 1;

        when(vehiculoRepository.findById(idVehiculo)).thenReturn(empty());

        assertThrows(NotFoundException.class, () -> serviceDispositivo.crearDispositivo(dispositivoRequestDTO, idVehiculo));
        verify(vehiculoRepository, times(1)).findById(idVehiculo);
        verify(vehiculoRepository, never()).save(any(Vehiculo.class));
    }

    @Test
    void obtenerDispositvoLanzaExcepcion(){
        when(dispositivoRepository.findByUnicoId(dispositivoRequestDTO.getUniqueId())).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class,() -> serviceDispositivo.obtenerDispositivo(dispositivoRequestDTO.getUniqueId()));

        verify(dispositivoRepository,times(1)).findByUnicoId(anyString());

    }

    @Test
    public void crearDispositivo_seGuardaDispositivo() {
        Integer idVehiculo = 1;
        Vehiculo vehiculo = Vehiculo
                .builder()
                .id(idVehiculo)
                .build();

        when(vehiculoRepository.findById(idVehiculo)).thenReturn(Optional.of(vehiculo));

        serviceDispositivo.crearDispositivo(dispositivoRequestDTO, idVehiculo);
        verify(vehiculoRepository, times(1)).findById(idVehiculo);
    }

    @Test
    void obtenerDispositvo(){
        when(dispositivoRepository.findByUnicoId(dispositivoRequestDTO.getUniqueId())).thenReturn(Optional.of(dispositivo));
        serviceDispositivo.obtenerDispositivo(dispositivoRequestDTO.getUniqueId());
        assertEquals(dispositivo.getUnicoId(),"uniqueId");
        assertEquals(dispositivo.getNombre(),"disp");
        assertEquals(dispositivo.getId(),1);
    }

    public void verificarNoRegistroDeDispositivo(){
        assertThrows(BadRequestException.class,() -> serviceDispositivo.crearDispositivo(dispositivoRequestDTO,1));
        verify(dispositivoRepository,never()).save(any(Dispositivo.class));
    }

}
