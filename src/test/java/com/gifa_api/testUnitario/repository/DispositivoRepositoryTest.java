package com.gifa_api.testUnitario.repository;

import com.gifa_api.model.Dispositivo;
import com.gifa_api.model.Vehiculo;
import com.gifa_api.repository.IDispositivoRepository;
import com.gifa_api.repository.IVehiculoRepository;
import com.gifa_api.utils.enums.EstadoDeHabilitacion;
import com.gifa_api.utils.enums.EstadoVehiculo;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class DispositivoRepositoryTest {

    @Autowired
    private IDispositivoRepository dispositivoRepository;

    @Autowired
    private IVehiculoRepository vehiculoRepository;

    @Test
    @Transactional
    @Rollback
    void guardarDispositivo() {
        Vehiculo vehiculo = Vehiculo.builder()
                .patente("ABC123")
                .antiguedad(5)
                .kilometrajeUsado(10000)
                .modelo("Toyota")
                .estadoDeHabilitacion(EstadoDeHabilitacion.HABILITADO)
                .estadoVehiculo(EstadoVehiculo.REPARADO)
                .fechaVencimiento(null)
                .build();

        // Primero guarda el vehículo
        Vehiculo vehiculoGuardado = vehiculoRepository.save(vehiculo);

        Dispositivo dispositivo = Dispositivo.builder()
                .unicoId("1")
                .nombre("vehiculazo")
                .vehiculo(vehiculoGuardado) // Asociar el vehículo guardado
                .build();

        Dispositivo dispositivoGuardado = dispositivoRepository.save(dispositivo);

        assertNotNull(dispositivoGuardado);
        assertNotNull(dispositivoGuardado.getId());
        assertEquals("1", dispositivoGuardado.getUnicoId());
        assertEquals("vehiculazo", dispositivoGuardado.getNombre());
        assertEquals(vehiculoGuardado, dispositivoGuardado.getVehiculo());
    }

    @Test
    @Transactional
    @Rollback
    void findByUnicoId() {
        Vehiculo vehiculo = Vehiculo.builder()
                .patente("ABC123")
                .antiguedad(5)
                .kilometrajeUsado(10000)
                .modelo("Toyota")
                .estadoDeHabilitacion(EstadoDeHabilitacion.HABILITADO)
                .estadoVehiculo(EstadoVehiculo.REPARADO)
                .fechaVencimiento(null)
                .build();

        // Primero guarda el vehículo
        Vehiculo vehiculoGuardado = vehiculoRepository.save(vehiculo);

        Dispositivo dispositivo = Dispositivo.builder()
                .unicoId("1")
                .nombre("vehiculazo")
                .vehiculo(vehiculoGuardado) // Asociar el vehículo guardado
                .build();

        dispositivoRepository.save(dispositivo);

        Optional<Dispositivo> dispositivoEncontrado = dispositivoRepository.findByUnicoId("1");

        assertTrue(dispositivoEncontrado.isPresent());
        assertEquals("vehiculazo", dispositivoEncontrado.get().getNombre());
    }

    @Test
    @Transactional
    @Rollback
    void findVehiculosDeDispositivo() {
        Vehiculo vehiculo = Vehiculo.builder()
                .patente("ABC123")
                .antiguedad(5)
                .kilometrajeUsado(10000)
                .modelo("Toyota")
                .estadoDeHabilitacion(EstadoDeHabilitacion.HABILITADO)
                .estadoVehiculo(EstadoVehiculo.REPARADO)
                .fechaVencimiento(null)
                .build();

        // Primero guarda el vehículo
        Vehiculo vehiculoGuardado = vehiculoRepository.save(vehiculo);

        Dispositivo dispositivo = Dispositivo.builder()
                .unicoId("1")
                .nombre("vehiculazo")
                .vehiculo(vehiculoGuardado) // Asociar el vehículo guardado
                .build();

        dispositivoRepository.save(dispositivo);

        Optional<Vehiculo> vehiculoEncontrado = dispositivoRepository.findVehiculoDeDispositivo("1");

        assertTrue(vehiculoEncontrado.isPresent());
        assertEquals(vehiculoGuardado.getPatente(), vehiculoEncontrado.get().getPatente());
    }

    @Test
    @Transactional
    @Rollback
    void existsByUnicoId() {
        Vehiculo vehiculo = Vehiculo.builder()
                .patente("ABC123")
                .antiguedad(5)
                .kilometrajeUsado(10000)
                .modelo("Toyota")
                .estadoDeHabilitacion(EstadoDeHabilitacion.HABILITADO)
                .estadoVehiculo(EstadoVehiculo.REPARADO)
                .fechaVencimiento(null)
                .build();

        // Primero guarda el vehículo
        Vehiculo vehiculoGuardado = vehiculoRepository.save(vehiculo);

        Dispositivo dispositivo = Dispositivo.builder()
                .unicoId("1")
                .nombre("vehiculazo")
                .vehiculo(vehiculoGuardado) // Asociar el vehículo guardado
                .build();

        dispositivoRepository.save(dispositivo);

        assertTrue(dispositivoRepository.existsByUnicoId("1"));
    }
}
