package com.gifa_api.testUnitario;

import com.gifa_api.dto.mantenimiento.RegistrarMantenimientoDTO;
import com.gifa_api.dto.vehiculo.ListaVehiculosResponseDTO;
import com.gifa_api.dto.vehiculo.RegistarVehiculoDTO;
import com.gifa_api.exception.NotFoundException;
import com.gifa_api.model.Tarjeta;
import com.gifa_api.model.Vehiculo;
import com.gifa_api.repository.ITarjetaRepository;
import com.gifa_api.repository.IVehiculoRepository;
import com.gifa_api.service.IMantenimientoService;
import com.gifa_api.service.impl.VehiculoServiceImpl;
import com.gifa_api.utils.enums.EstadoDeHabilitacion;
import com.gifa_api.utils.mappers.VehiculoMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class VehiculoServiceImplTest {

    @Mock
    private IVehiculoRepository vehiculoRepository;

    @Mock
    private IMantenimientoService mantenimientoService;

    @Mock
    private ITarjetaRepository tarjetaRepository;

    @Mock
    private VehiculoMapper vehiculoMapper;

    @InjectMocks
    private VehiculoServiceImpl vehiculoService;

    @Test
    void inhabilitar_lanzaExcepcionVehiculoNoExiste(){
        when(vehiculoRepository.findById(anyInt())).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class , () -> vehiculoService.inhabilitar(anyInt()));
        verify(vehiculoRepository,times(1)).findById(anyInt());
        verify(vehiculoRepository,never()).save(any(Vehiculo.class));

    }

    @Test
    void habilitar_lanzaExcepcionVehiculoNoExiste(){
        when(vehiculoRepository.findById(anyInt())).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class , () -> vehiculoService.habilitar(anyInt()));
        verify(vehiculoRepository,times(1)).findById(anyInt());
        verify(vehiculoRepository,never()).save(any(Vehiculo.class));

    }

    @Test
    void getVehiculos_debeRetornarListaVehiculosResponseDTO() {
        List<Vehiculo> vehiculos = Collections.singletonList(new Vehiculo());
        ListaVehiculosResponseDTO listaResponseDTO = new ListaVehiculosResponseDTO();
        when(vehiculoRepository.findAll()).thenReturn(vehiculos);
        when(vehiculoMapper.toListaVehiculosResponseDTO(vehiculos)).thenReturn(listaResponseDTO);

        ListaVehiculosResponseDTO result = vehiculoService.getVehiculos();

        assertEquals(listaResponseDTO, result);
        verify(vehiculoRepository, times(1)).findAll();
        verify(vehiculoMapper, times(1)).toListaVehiculosResponseDTO(vehiculos);
    }

    @Test
    void registrar_debeGuardarVehiculoYTarjeta() {

        LocalDate fechaRegistro = LocalDate.now();
        RegistarVehiculoDTO vehiculoDTO = new RegistarVehiculoDTO("ABC123", 5, 100000,4, "Modelo X",fechaRegistro);
        when(tarjetaRepository.save(any(Tarjeta.class))).thenReturn(new Tarjeta());

        vehiculoService.registrar(vehiculoDTO);

        verify(tarjetaRepository, times(1)).save(any(Tarjeta.class));
        verify(vehiculoRepository, times(1)).save(any(Vehiculo.class));
    }

    @Test
    void inhabilitar_debeInhabilitarVehiculo() {
        Vehiculo vehiculo = new Vehiculo();
        when(vehiculoRepository.findById(anyInt())).thenReturn(Optional.of(vehiculo));

        vehiculoService.inhabilitar(1);

        verify(vehiculoRepository, times(1)).findById(1);
        verify(vehiculoRepository, times(1)).save(vehiculo);
        assertEquals(EstadoDeHabilitacion.INHABILITADO, vehiculo.getEstadoDeHabilitacion());
    }

    @Test
    void habilitar_debeHabilitarVehiculo() {
        Vehiculo vehiculo = new Vehiculo();
        when(vehiculoRepository.findById(anyInt())).thenReturn(Optional.of(vehiculo));

        vehiculoService.habilitar(1);

        verify(vehiculoRepository, times(1)).findById(1);
        verify(vehiculoRepository, times(1)).save(vehiculo);
        assertEquals(EstadoDeHabilitacion.HABILITADO, vehiculo.getEstadoDeHabilitacion());
    }

    @Test
    void verificarFechaVencimiento_debeCrearMantenimientoSiFechaVencida() {
        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setFechaVencimiento(LocalDate.now().minusDays(1));
        vehiculo.setId(1);
        when(vehiculoRepository.findAll()).thenReturn(Collections.singletonList(vehiculo));

        vehiculoService.verificarFechaVencimiento();

        verify(mantenimientoService, times(1)).crearMantenimiento(any(RegistrarMantenimientoDTO.class));
    }
}
