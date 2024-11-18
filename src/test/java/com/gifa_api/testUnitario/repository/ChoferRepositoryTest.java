package com.gifa_api.testUnitario.repository;

import com.gifa_api.model.Chofer;
import com.gifa_api.model.Tarjeta;
import com.gifa_api.model.Usuario;
import com.gifa_api.model.Vehiculo;
import com.gifa_api.repository.IChoferRepository;
import com.gifa_api.repository.ITarjetaRepository;
import com.gifa_api.repository.IUsuarioRepository;
import com.gifa_api.repository.IVehiculoRepository;
import com.gifa_api.utils.enums.EstadoDeHabilitacion;
import com.gifa_api.utils.enums.EstadoUsuario;
import com.gifa_api.utils.enums.EstadoVehiculo;
import com.gifa_api.utils.enums.Rol;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class ChoferRepositoryTest {
    @Autowired
    private IChoferRepository choferRepository;

    @Autowired
    private IVehiculoRepository vehiculoRepository;

    @Autowired
    private ITarjetaRepository tarjetaRepository;

    @Autowired
    private IUsuarioRepository usuarioRepository;


    private Usuario usuario;
    private Tarjeta tarjeta;
    private Vehiculo vehiculo;
    private Chofer chofer;

    @BeforeEach
    void setUp(){
        Integer id = 1;
        usuario = Usuario.builder()
                .usuario("usuario")
                .contrasena("contrasena")
                .rol(Rol.CHOFER)
                .estadoUsuario(EstadoUsuario.HABILITADO)
                .build();
        usuarioRepository.save(usuario);

        tarjeta = Tarjeta.builder().numero(123).build();
        tarjetaRepository.save(tarjeta);

        vehiculo =  Vehiculo.builder().patente("ABC123").antiguedad(3).kilometrajeUsado(50000).modelo("Hilux").estadoDeHabilitacion(EstadoDeHabilitacion.HABILITADO).estadoVehiculo(EstadoVehiculo.REPARADO).fechaVencimiento(LocalDate.now().plusYears(1)).tarjeta(tarjeta) .build(); // Asignar la tarjeta guardada.build();
        vehiculoRepository.save(vehiculo);

        chofer = Chofer.builder().usuario(usuario).nombre("chofer").vehiculo(vehiculo).id(1).build();

    }

    @Test
    @Transactional
    @Rollback
    void guardarChofer() {
        Chofer choferGuardado = choferRepository.save(chofer);
        assertNotNull(choferGuardado);
        assertNotNull(choferGuardado.getId());
        assertEquals(usuario.getId(), choferGuardado.getUsuario().getId());
        assertEquals(usuario.getPassword(), choferGuardado.getUsuario().getPassword());
        assertEquals(usuario.getContrasena(), choferGuardado.getUsuario().getContrasena());
        assertEquals(usuario.getEstadoUsuario(), choferGuardado.getUsuario().getEstadoUsuario());
    }

    @Test
    @Transactional
    @Rollback
    void findByUsuarioId() {
        choferRepository.save(chofer);
        Optional<Chofer> choferEncontrado = choferRepository.findByUsuario_Id(usuario.getId());

        assertTrue(choferEncontrado.isPresent());
        assertEquals("chofer", choferEncontrado.get().getNombre());
        assertEquals(usuario.getId(), choferEncontrado.get().getUsuario().getId());
    }

    @Test
    @Transactional
    @Rollback
    void obtenerNombreDeChofersDeVehiculo() {
        choferRepository.save(chofer);
        List<String> nombresChoferes = choferRepository.obtenerNombreDeChofersDeVehiculo(vehiculo.getId());
        assertEquals(1, nombresChoferes.size());
        assertEquals("chofer", nombresChoferes.get(0));
    }

    @Test
    @Transactional
    @Rollback
    void findVehiculoByChofer(){
        choferRepository.save(chofer);
        Vehiculo vehiculoGuardado = choferRepository.findVehiculoByChofer(chofer.getUsuario().getId());
        assertEquals(vehiculoGuardado.getId(),vehiculo.getId());
        assertEquals(vehiculoGuardado.getEstadoVehiculo(),vehiculo.getEstadoVehiculo());
        assertEquals(vehiculoGuardado.getModelo(),vehiculo.getModelo());
        assertEquals(vehiculoGuardado.getKilometrajeUsado(),vehiculo.getKilometrajeUsado());
    }

// no se usa esta query
//    @Test
//    @Transactional
//    @Rollback
//    void findByIdWithVehiculo_devuelveChoferQueMatcheanConUnVehiculo(){
//       Optional<Chofer> choferAsociadoAvehiculo= choferRepository.findByIdWithVehiculo(vehiculo.getId());
//
//       assertEquals(chofer.getId(),choferAsociadoAvehiculo.get().getId());
//       assertEquals(chofer.getNombre(),choferAsociadoAvehiculo.get().getNombre());
//       assertEquals(vehiculo.getId(),choferAsociadoAvehiculo.get().getVehiculo().getId());
//    }
}