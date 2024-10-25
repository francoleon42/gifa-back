package com.gifa_api.testUnitario.service;

import com.gifa_api.dto.traccar.CrearDispositivoRequestDTO;
import com.gifa_api.exception.NotFoundException;

import com.gifa_api.model.Vehiculo;
import com.gifa_api.repository.IVehiculoRepository;
import com.gifa_api.service.impl.DispositivoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DispositivoServiceImplTest {
    @Mock
    private IVehiculoRepository vehiculoRepository;



    @InjectMocks
    private DispositivoServiceImpl serviceDispositivo;

    private CrearDispositivoRequestDTO dispositivoRequestDTO;

    @BeforeEach
    void setUp(){
         dispositivoRequestDTO = CrearDispositivoRequestDTO.builder().name("name").uniqueId("uniqueid").build();
    }

    // Tests originales
    @Test
    public void crearDispositivo_vehiculoInvalidoExcepcion() {
        Integer idVehiculo = 1;

        when(vehiculoRepository.findById(idVehiculo)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> serviceDispositivo.crearDispositivo(dispositivoRequestDTO, idVehiculo));
        verify(vehiculoRepository, times(1)).findById(idVehiculo);
        verify(vehiculoRepository, never()).save(any(Vehiculo.class));
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
    void crearDispositivo_nombreNoPuedeSerVacio(){
        dispositivoRequestDTO.setName("");
        assertThrows(IllegalArgumentException.class,() -> serviceDispositivo.crearDispositivo(dispositivoRequestDTO,1));
    }

    @Test
    void crearDispositivo_uniqueIdNoPuedeSerVacio(){
        dispositivoRequestDTO.setName("");
        assertThrows(IllegalArgumentException.class,() -> serviceDispositivo.crearDispositivo(dispositivoRequestDTO,1));
    }


//    @Test
//    public void calcularKmDeDispositivoDespuesDeFecha_formulaHaversineCorrecta() {
//        String unicoId = "12345";
//        OffsetDateTime fecha = OffsetDateTime.now().minusDays(1);
//
//        // Creación de posiciones utilizando el patrón Builder
//        Posicion pos1 =  Posicion.builder()
//                .latitude(10.0)
//                .longitude(10.0)
//                .build();
//
//        Posicion pos2 =  Posicion.builder()
//                .latitude(20.0)
//                .longitude(20.0)
//                .build();
//
//        List<Posicion> posiciones = Arrays.asList(pos1, pos2);
//
//        when(posicionRepository.findByUnicoIdAndDespuesFecha(unicoId, fecha)).thenReturn(posiciones);
//
//        // Se espera que se calcule la distancia entre las posiciones
//        int kilometraje = serviceDispositivo.calcularKmDeDispositivoDespuesDeFecha(unicoId, fecha);
//
//        // Verificación de que se utilizó la fórmula de Haversine
//        verify(posicionRepository, times(1)).findByUnicoIdAndDespuesFecha(unicoId, fecha);
//        assertEquals(1512, kilometraje); // Valor esperado basado en la fórmula Haversine
//    }
//
//    // Test para actualizarKilometrajeDeVehiculos (invocado indirectamente)
//    @Test
//    public void calcularKmDeDispositivoDespuesDeFecha_actualizaKilometrajeVehiculo() {
//        String unicoId = "12345";
//        OffsetDateTime fecha = OffsetDateTime.now().minusDays(1);
//
//        Dispositivo dispositivo = new Dispositivo();
//        dispositivo.setUnicoId(unicoId);
//
//        Posicion pos1 =  Posicion.builder()
//                .latitude(10.0)
//                .longitude(10.0)
//                .build();
//
//        Posicion pos2 =  Posicion.builder()
//                .latitude(20.0)
//                .longitude(20.0)
//                .build();
//
//        List<Posicion> posiciones = Arrays.asList(pos1, pos2);
//
//        Vehiculo vehiculo = new Vehiculo();
//        vehiculo.setKilometraje(1000);
//
//        when(dispositivoRepository.findByUnicoId(unicoId)).thenReturn(Optional.of(dispositivo));
//        when(posicionRepository.findByUnicoId(unicoId)).thenReturn(posiciones);
//        when(dispositivoRepository.findVehiculosDeDispositivo(unicoId)).thenReturn(Optional.of(vehiculo));
//
//        // Método indirecto que actualiza el kilometraje
//        serviceDispositivo.calcularKmDeDispositivoDespuesDeFecha(unicoId, fecha);
//
//        // Verificación de que el kilometraje se actualiza correctamente
//        verify(dispositivoRepository, times(1)).findVehiculosDeDispositivo(unicoId);
//        verify(kilometrajeVehiculoService, times(1)).addKilometrajeVehiculo(anyInt(), any(OffsetDateTime.class), eq(vehiculo.getId()));
//        verify(vehiculoRepository, times(1)).save(vehiculo);
//    }
}
