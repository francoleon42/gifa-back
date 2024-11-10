//package com.gifa_api.testUnitario.repository;
//
//import com.gifa_api.model.Dispositivo;
//import com.gifa_api.model.Posicion;
//import com.gifa_api.repository.IDispositivoRepository;
//import com.gifa_api.repository.IPosicionRepository;
//import com.gifa_api.repository.IVehiculoRepository;
//import com.gifa_api.utils.enums.EstadoDeHabilitacion;
//import com.gifa_api.utils.enums.EstadoVehiculo;
//import jakarta.transaction.Transactional;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.test.annotation.Rollback;
//
//import java.time.LocalDate;
//import java.time.OffsetDateTime;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.Assert.assertEquals;
//
//@DataJpaTest
//public class PosicionRepositoryTest {
//    @Autowired
//    private IPosicionRepository posicionRepository;
//    @Autowired
//    private IDispositivoRepository dispositivoRepository;
//    @Autowired
//    private IVehiculoRepository vehiculoRepository;
//
//    private Posicion posicion;
//    private Posicion posicion1;
//    private Posicion posicion2;
//    private Dispositivo dispositivo;
//    @BeforeEach
//    void setUp(){
//        dispositivo = Dispositivo.
//                builder().
//                unicoId("1")
//                .nombre("dispositivo")
//                .build();
//        dispositivoRepository.save(dispositivo);
//
//        posicion1 = Posicion.builder()
//                .latitude(10.0)
//                .longitude(10.0)
//                .fechaHora(OffsetDateTime.now())
//                .dispositivo(dispositivo)
//                .build();
//
//        posicion2 = Posicion.builder()
//                .latitude(10.0)
//                .longitude(10.0)
//                .fechaHora(OffsetDateTime.now().plusDays(1))
//                .dispositivo(dispositivo)
//                .build();
//        posicionRepository.saveAll(List.of(posicion1,posicion2));
//
//        posicion = Posicion.builder()
//                .latitude(10.0)
//                .longitude(10.0)
//                .fechaHora(OffsetDateTime.now())
//                .dispositivo(dispositivo)
//                .build();
//
//    }
//
//    @Test
//    @Transactional
//    @Rollback
//    void registrarPosicion(){
//        Posicion posicionGuardada = posicionRepository.save(posicion);
//        assertEquals(posicionGuardada.getId(),posicion.getId());
//
//    }
//
//    @Test
//    @Transactional
//    @Rollback
//    void findByUnicoId_devuelveLasPosicionesDeUnDispositivo(){
//        List<Posicion> posiciones = posicionRepository.findByUnicoId(dispositivo.getUnicoId());
//        assertEquals(posiciones.size(),2);
//        assertEquals(posiciones.get(0).getId(),posicion1.getId());
//        assertEquals(posiciones.get(1).getId(),posicion2.getId());
//    }
//
//    @Test
//    @Transactional
//    @Rollback
//    void findByUnicoIdAndDespuesFecha_devuelveLasPosicionesDeUnDispositivoQuePasanLaFechaSeleccionada(){
//        List<Posicion> posiciones = posicionRepository.findByUnicoIdAndDespuesFecha(dispositivo.getUnicoId(),OffsetDateTime.now());
//        assertEquals(posiciones.size(),1);
//        assertEquals(posiciones.get(0).getId(),posicion2.getId());
//    }
//
////    @Test
////    @Transactional
////    @Rollback
////    void obtenerPosicionDeDispositivoPorFecha(){
////        Optional <Posicion> posicionFecha = posicionRepository.obtenerPosicionDeDispositivoPorFecha(dispositivo.getUnicoId(),OffsetDateTime.now());
////
////        assertEquals(posicionFecha.get().getId(),posicion1.getId());
////    }
//
//    @Test
//    @Transactional
//    @Rollback
//    void obtenerUltimaPosicion_devuelveUltimaPosicionDeUnDispositivo(){
//        List <Posicion> posicionFecha = posicionRepository.obtenerUltimaPosicion(dispositivo.getUnicoId());
//
//        assertEquals(posicionFecha.get(0).getId(),posicion2.getId());
//    }
//
//
//}
