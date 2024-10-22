package com.gifa_api.testUnitario.repository;

import com.gifa_api.model.Chofer;
import com.gifa_api.model.Usuario;
import com.gifa_api.model.Vehiculo;
import com.gifa_api.repository.IChoferRepository;
import com.gifa_api.repository.IVehiculoRepository;
import com.gifa_api.utils.enums.EstadoChofer;
import com.gifa_api.utils.enums.EstadoDeHabilitacion;
import com.gifa_api.utils.enums.EstadoVehiculo;
import com.gifa_api.utils.enums.Rol;
import jakarta.transaction.Transactional;
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

    @Test
    @Transactional
    @Rollback
    void guardarChofer() {
        Usuario usuario = Usuario.builder()
                .usuario("usuario")
                .contrasena("contrasena")
                .rol(Rol.CHOFER)
                .build();

        Chofer chofer = Chofer.builder()
                .usuario(usuario)
                .estadoChofer(EstadoChofer.HABILITADO)
                .nombre("chofer")
                .build();

        Chofer choferGuardado = choferRepository.save(chofer);

        assertNotNull(choferGuardado);
        assertNotNull(choferGuardado.getId());
        assertEquals(usuario, choferGuardado.getUsuario());
        assertEquals(chofer.getEstadoChofer(), choferGuardado.getEstadoChofer());
    }

    @Test
    @Transactional
    @Rollback
    void findByUsuarioId() {
        Usuario usuario = Usuario.builder()
                .usuario("usuario2")
                .contrasena("contrasena")
                .rol(Rol.CHOFER)
                .build();

        Chofer chofer = Chofer.builder()
                .usuario(usuario)
                .estadoChofer(EstadoChofer.HABILITADO)
                .nombre("chofer2")
                .build();

        choferRepository.save(chofer);

        Optional<Chofer> choferEncontrado = choferRepository.findByUsuario_Id(usuario.getId());

        assertTrue(choferEncontrado.isPresent());
        assertEquals("chofer2", choferEncontrado.get().getNombre());
        assertEquals(usuario.getId(), choferEncontrado.get().getUsuario().getId());
    }

    @Test
    @Transactional
    @Rollback
    void obtenerNombreDeChofersDeVehiculo() {
        Usuario usuario = Usuario.builder()
                .usuario("usuario3")
                .contrasena("contrasena")
                .rol(Rol.CHOFER)
                .build();

        Vehiculo vehiculo = Vehiculo.builder()
                .patente("ABC123")
                .antiguedad(3)
                .kilometraje(50000)
                .modelo("Hilux")
                .estadoDeHabilitacion(EstadoDeHabilitacion.HABILITADO)
                .estadoVehiculo(EstadoVehiculo.REPARADO)
                .fechaVencimiento(LocalDate.now().plusYears(1))
                .tarjeta(null)
                .build();

        Vehiculo vehiculoGuardado = vehiculoRepository.save(vehiculo);

        Chofer chofer = Chofer.builder()
                .usuario(usuario)
                .estadoChofer(EstadoChofer.HABILITADO)
                .nombre("chofer3")
                .vehiculo(vehiculoGuardado)  // Asignar el vehículo ya guardado
                .build();

        choferRepository.save(chofer);

        List<String> nombresChoferes = choferRepository.obtenerNombreDeChofersDeVehiculo(vehiculoGuardado.getId());

        assertNotNull(nombresChoferes);
        assertEquals(1, nombresChoferes.size());
        assertEquals("chofer3", nombresChoferes.get(0));
    }

}
