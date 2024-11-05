//package com.gifa_api.testUnitario.repository;
//
//import com.gifa_api.model.Chofer;
//import com.gifa_api.model.Tarjeta;
//import com.gifa_api.model.Usuario;
//import com.gifa_api.model.Vehiculo;
//import com.gifa_api.repository.IChoferRepository;
//import com.gifa_api.repository.ITarjetaRepository;
//import com.gifa_api.repository.IVehiculoRepository;
//import com.gifa_api.utils.enums.EstadoDeHabilitacion;
//import com.gifa_api.utils.enums.EstadoVehiculo;
//import com.gifa_api.utils.enums.Rol;
//import jakarta.transaction.Transactional;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.test.annotation.Rollback;
//
//import java.time.LocalDate;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@DataJpaTest
//public class ChoferRepositoryTest {
//    @Autowired
//    private IChoferRepository choferRepository;
//
//    @Autowired
//    private IVehiculoRepository vehiculoRepository;
//
//    @Autowired
//    private ITarjetaRepository tarjetaRepository;
//
//    private Usuario usuario;
//    private Tarjeta tarjeta;
//    private Vehiculo vehiculo;
//    private Chofer chofer;
//
//    @BeforeEach
//    void setUp(){
//        usuario =Usuario.builder().usuario("usuario").contrasena("contrasena").rol(Rol.CHOFER).build();
//        tarjeta = Tarjeta.builder().build();
//        tarjetaRepository.save(tarjeta);
//
//        vehiculo =  Vehiculo.builder().patente("ABC123").antiguedad(3).kilometraje(50000).modelo("Hilux").estadoDeHabilitacion(EstadoDeHabilitacion.HABILITADO).estadoVehiculo(EstadoVehiculo.REPARADO).fechaVencimiento(LocalDate.now().plusYears(1)).tarjeta(tarjeta) .build(); // Asignar la tarjeta guardada.build();
//        vehiculoRepository.save(vehiculo);
//
//        chofer = Chofer.builder().usuario(usuario).estadoChofer(EstadoChofer.HABILITADO).nombre("chofer").vehiculo(vehiculo).build();
//        choferRepository.save(chofer);
//    }
//
//    @Test
//    @Transactional
//    @Rollback
//    void guardarChofer() {
//        Chofer choferGuardado = choferRepository.save(chofer);
//
//        assertNotNull(choferGuardado);
//        assertNotNull(choferGuardado.getId());
//        assertEquals(usuario, choferGuardado.getUsuario());
//        assertEquals(chofer.getEstadoChofer(), choferGuardado.getEstadoChofer());
//    }
//
//    @Test
//    @Transactional
//    @Rollback
//    void findByUsuarioId() {
//        Optional<Chofer> choferEncontrado = choferRepository.findByUsuario_Id(usuario.getId());
//
//        assertTrue(choferEncontrado.isPresent());
//        assertEquals("chofer", choferEncontrado.get().getNombre());
//        assertEquals(usuario.getId(), choferEncontrado.get().getUsuario().getId());
//    }
//
//    @Test
//    @Transactional
//    @Rollback
//    void obtenerNombreDeChofersDeVehiculo() {
//        List<String> nombresChoferes = choferRepository.obtenerNombreDeChofersDeVehiculo(vehiculo.getId());
//        assertEquals(1, nombresChoferes.size());
//        assertEquals("chofer", nombresChoferes.get(0));
//    }
//
//
////    @Test
////    @Transactional
////    @Rollback
////    void findByIdWithVehiculo_devuelveChoferQueMatcheanConUnVehiculo(){
////       Optional<Chofer> choferAsociadoAvehiculo= choferRepository.findByIdWithVehiculo(vehiculo.getId());
////
////       assertEquals(chofer.getId(),choferAsociadoAvehiculo.get().getId());
////       assertEquals(chofer.getEstadoChofer(),choferAsociadoAvehiculo.get().getEstadoChofer());
////       assertEquals(chofer.getNombre(),choferAsociadoAvehiculo.get().getNombre());
////       assertEquals(vehiculo.getId(),choferAsociadoAvehiculo.get().getVehiculo().getId());
////    }
//}