package com.gifa_api.testUnitario.service;

import com.gifa_api.client.ITraccarCliente;

import com.gifa_api.model.Posicion;
import com.gifa_api.repository.IPosicionRepository;
import com.gifa_api.service.IDispositivoService;
import com.gifa_api.service.ITraccarService;
import com.gifa_api.service.impl.PosicionServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PosicionServiceImplTest {

    @Mock
    private IPosicionRepository posicionRepository;

    @Mock
    private ITraccarCliente traccarCliente;

    @Mock
    private ITraccarService traccarService;

    @Mock
    private IDispositivoService dispositivoService;

    @InjectMocks
    private PosicionServiceImpl posicionService;

//    @Test
//    void actualizarPosicionesDeDispositivo_deberiaActualizarPosiciones() {
//        List<ObtenerDispositivoRequestDTO> dispositivosDTO = List.of(new ObtenerDispositivoRequestDTO(1, "uniqueId","name","status"));
//        when(traccarService.obtenerDispositivos()).thenReturn(dispositivosDTO);
//
//        Dispositivo dispositivo = new Dispositivo();
//        when(dispositivoService.obtenerDispositivo("uniqueId")).thenReturn(dispositivo);
//
//        PosicionDispositivoDTO posicionDTO = new PosicionDispositivoDTO(12.34, 56.78, OffsetDateTime.now());
//        List<PosicionDispositivoDTO> posiciones = List.of(posicionDTO);
//
//        when(traccarCliente.getPosicionDispositivoTraccar(eq(1), anyString(), anyString())).thenReturn(posiciones);
//
//        when(posicionRepository.obtenerPosicionDeDispositivoPorFecha(anyString(), any(OffsetDateTime.class))).thenReturn(Optional.empty());
//
//        posicionService.actualizarPosicionesDeDispositivo();
//
//        verify(posicionRepository, times(1)).save(any(Posicion.class));
//    }
//
//
//    @Test
//    void actualizarPosicionesDeDispositivo_noDeberiaGuardarSiLaPosicionYaExiste() {
//        List<ObtenerDispositivoRequestDTO> dispositivosDTO = List.of(new ObtenerDispositivoRequestDTO(1, "uniqueId","name","status"));
//        when(traccarService.obtenerDispositivos()).thenReturn(dispositivosDTO);
//
//        Dispositivo dispositivo = new Dispositivo();
//        when(dispositivoService.obtenerDispositivo("uniqueId")).thenReturn(dispositivo);
//
//        PosicionDispositivoDTO posicionDTO = new PosicionDispositivoDTO(12.34, 56.78, OffsetDateTime.now());
//        List<PosicionDispositivoDTO> posiciones = List.of(posicionDTO);
//        when(traccarCliente.getPosicionDispositivoTraccar(1, anyString(), anyString())).thenReturn(posiciones);
//
//        when(posicionRepository.obtenerPosicionDeDispositivoPorFecha(anyString(), any(OffsetDateTime.class))).thenReturn(Optional.of(new Posicion()));
//
//        posicionService.actualizarPosicionesDeDispositivo();
//
//        verify(posicionRepository, never()).save(any(Posicion.class));
//    }

    @Test
    void getPosicionesDeDispositivo_deberiaRetornarListaDePosiciones() {
        List<Posicion> posiciones = List.of(new Posicion());
        when(posicionRepository.findByUnicoId("uniqueId")).thenReturn(posiciones);

        List<Posicion> result = posicionService.getPosicionesDeDispositivo("uniqueId");

        verify(posicionRepository, times(1)).findByUnicoId("uniqueId");
        assertEquals(posiciones, result);
    }

    @Test
    void getPosicionesDeDispositivoDespuesDeFecha_deberiaRetornarListaDePosiciones() {
        OffsetDateTime fecha = OffsetDateTime.now();
        List<Posicion> posiciones = List.of(new Posicion());
        when(posicionRepository.findByUnicoIdAndDespuesFecha("uniqueId", fecha)).thenReturn(posiciones);

        List<Posicion> result = posicionService.getPosicionesDeDispositivoDespuesDeFecha("uniqueId", fecha);

        verify(posicionRepository, times(1)).findByUnicoIdAndDespuesFecha("uniqueId", fecha);
        assertEquals(posiciones, result);
    }
}
