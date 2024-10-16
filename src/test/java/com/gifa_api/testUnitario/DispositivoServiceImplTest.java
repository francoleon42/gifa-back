package com.gifa_api.testUnitario;

import com.gifa_api.dto.traccar.CrearDispositivoRequestDTO;
import com.gifa_api.exception.NotFoundException;
import com.gifa_api.model.Dispositivo;
import com.gifa_api.model.Posicion;
import com.gifa_api.model.Vehiculo;
import com.gifa_api.repository.IDispositivoRepository;
import com.gifa_api.repository.IPosicionRepository;
import com.gifa_api.repository.IVehiculoRepository;
import com.gifa_api.service.impl.DispositivoServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DispositivoServiceImplTest {

    @Mock
    private IDispositivoRepository dispositivoRepository;

    @Mock
    private IVehiculoRepository vehiculoRepository;

    @Mock
    private IPosicionRepository posicionRepository;

    @InjectMocks
    private DispositivoServiceImpl serviceDispositivo;

    // Tests originales
    @Test
    public void crearDispositivo_vehiculoInvalidoExcepcion() {
        Integer idVehiculo = 1;
        CrearDispositivoRequestDTO dispositivo = new CrearDispositivoRequestDTO();
        when(vehiculoRepository.findById(idVehiculo)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> serviceDispositivo.crearDispositivo(dispositivo, idVehiculo));
        verify(vehiculoRepository, times(1)).findById(idVehiculo);
        verify(dispositivoRepository, never()).save(any(Dispositivo.class));
    }

    @Test
    public void crearDispositivo_seGuardaDispositivo() {
        Integer idVehiculo = 1;
        CrearDispositivoRequestDTO dispositivo = new CrearDispositivoRequestDTO();

        when(vehiculoRepository.findById(idVehiculo)).thenReturn(Optional.of(new Vehiculo()));

        serviceDispositivo.crearDispositivo(dispositivo, idVehiculo);
        verify(vehiculoRepository, times(1)).findById(idVehiculo);
        verify(dispositivoRepository, times(1)).save(any(Dispositivo.class));
    }

//    // Tests añadidos para calcularDistanciaRecorrida
//    @Test
//    public void testCalcularDistanciaRecorrida() {
//        // Creamos posiciones simuladas
//        Posicion pos1 = Posicion.builder().latitude(-34.603722).longitude(-58.381592).build(); // Buenos Aires
//        Posicion pos2 = Posicion.builder().latitude(-34.609722).longitude(-58.381592).build(); // Punto cercano
//        Posicion pos3 = Posicion.builder().latitude(-34.615722).longitude(-58.381592).build(); // Punto más lejano
//
//        List<Posicion> posiciones = Arrays.asList(pos1, pos2, pos3);
//        when(posicionRepository.findByUnicoId("device123")).thenReturn(posiciones);
//
//
//        int distanciaRecorrida = serviceDispositivo.calcularDistanciaRecorrida("device123");
//
//        // Verificamos el resultado esperado. Para este caso, espera una distancia en kilómetros.
//        assertEquals(1, distanciaRecorrida); // Verifica si la distancia es correcta
//    }
//
//    @Test
//    public void testCalcularDistanciaConUnSoloPunto() {
//        Posicion pos1 = Posicion.builder().latitude(-34.603722).longitude(-58.381592).build(); // Buenos Aires
//
//        List<Posicion> posiciones = Arrays.asList(pos1);
//        when(posicionRepository.findByUnicoId("device123")).thenReturn(posiciones);
//
//        int distanciaRecorrida = serviceDispositivo.calcularDistanciaRecorrida("device123");
//
//        // Con solo una posición, la distancia recorrida debe ser 0
//        assertEquals(0, distanciaRecorrida);
//    }
}
