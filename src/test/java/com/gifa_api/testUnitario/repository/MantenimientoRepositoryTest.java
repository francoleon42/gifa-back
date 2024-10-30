package com.gifa_api.testUnitario.repository;

import com.gifa_api.model.Mantenimiento;
import com.gifa_api.model.Vehiculo;
import com.gifa_api.model.Usuario;
import com.gifa_api.repository.IMantenimientoRepository;
import com.gifa_api.repository.IUsuarioRepository;
import com.gifa_api.repository.IVehiculoRepository;
import com.gifa_api.utils.enums.EstadoMantenimiento;
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

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class MantenimientoRepositoryTest {

    @Autowired
    private IMantenimientoRepository mantenimientoRepository;
    @Autowired
    private IVehiculoRepository vehiculoRepository;

    @Autowired
    private IUsuarioRepository usuarioRepository;

    @Test
    @Transactional
    @Rollback
    void testGuardarMantenimiento() {
        // Crear un Vehiculo
        Vehiculo vehiculo1 = Vehiculo.builder()
                .patente("XYZ789")
                .antiguedad(3)
                .kilometraje(0)
                .modelo("Modelo Y")
                .estadoVehiculo(EstadoVehiculo.EN_REPARACION)
                .estadoDeHabilitacion(EstadoDeHabilitacion.HABILITADO)
                .fechaVencimiento(LocalDate.now().plusYears(1))
                .tarjeta(null)  // Tarjeta es null
                .build();

        // Crear un Usuario operador
        Usuario operador = Usuario.builder()
                .usuario("operador")
                .contrasena("$2a$10$RRAzywJFxaAG3pRlHXep6u6VNKi5KOTT3M8GCxDPHpAyZ0ofX2Bcu")
                .rol(Rol.OPERADOR)
                .build();

        // Crear un Mantenimiento
        Mantenimiento mantenimiento1 = Mantenimiento.builder()
                .fechaInicio(LocalDate.now())
                .fechaFinalizacion(LocalDate.now().plusDays(1))
                .asunto("Cambio de aceite")
                .estadoMantenimiento(EstadoMantenimiento.PENDIENTE)
                .operador(operador)
                .vehiculo(vehiculo1)
                .build();

        // Guardar el Mantenimiento
        mantenimiento1 = mantenimientoRepository.save(mantenimiento1);

        // Verificar que el Mantenimiento se haya guardado correctamente
        assertNotNull(mantenimiento1.getId());
    }

    @Test
    @Transactional
    @Rollback
    void testFindByVehiculoId() {
        // Crear y guardar un Vehiculo
        Vehiculo vehiculo1 = Vehiculo.builder()
                .id(1)
                .patente("XYZ789")
                .antiguedad(3)
                .kilometraje(0)
                .modelo("Modelo Y")
                .estadoVehiculo(EstadoVehiculo.EN_REPARACION)
                .estadoDeHabilitacion(EstadoDeHabilitacion.HABILITADO)
                .fechaVencimiento(LocalDate.now().plusYears(1))
                .tarjeta(null)
                .build();

        // Guardar el Vehiculo en la base de datos
        vehiculoRepository.save(vehiculo1);

        // Crear y guardar un Usuario operador
        Usuario operador = Usuario.builder()
                .usuario("operador")
                .contrasena("$2a$10$RRAzywJFxaAG3pRlHXep6u6VNKi5KOTT3M8GCxDPHpAyZ0ofX2Bcu")
                .rol(Rol.OPERADOR)
                .build();

        // Guardar el Usuario en la base de datos
        usuarioRepository.save(operador);

        // Crear y guardar un Mantenimiento
        Mantenimiento mantenimiento1 = Mantenimiento.builder()
                .fechaInicio(LocalDate.now())
                .fechaFinalizacion(LocalDate.now().plusDays(1))
                .asunto("Cambio de aceite")
                .estadoMantenimiento(EstadoMantenimiento.PENDIENTE)
                .operador(operador)
                .vehiculo(vehiculo1)
                .build();

        mantenimientoRepository.save(mantenimiento1);

        // Buscar Mantenimientos por ID de Vehículo
        List<Mantenimiento> mantenimientos = mantenimientoRepository.findByVehiculoId(vehiculo1.getId());

        // Verificar que se encuentre el mantenimiento
        assertFalse(mantenimientos.isEmpty());
        assertEquals(1, mantenimientos.size());
        assertEquals("Cambio de aceite", mantenimientos.get(0).getAsunto());
    }

    @Test
    @Transactional
    @Rollback
    void testFindAllByEstadoMantenimiento() {
        Vehiculo vehiculo1 = Vehiculo.builder()
                .patente("XYZ789")
                .antiguedad(3)
                .kilometraje(0)
                .modelo("Modelo Y")
                .estadoVehiculo(EstadoVehiculo.EN_REPARACION)
                .estadoDeHabilitacion(EstadoDeHabilitacion.HABILITADO)
                .fechaVencimiento(LocalDate.now().plusYears(1))
                .tarjeta(null)
                .build();

        vehiculoRepository.save(vehiculo1);


        Usuario operador = Usuario.builder()
                .usuario("operador")
                .contrasena("$2a$10$RRAzywJFxaAG3pRlHXep6u6VNKi5KOTT3M8GCxDPHpAyZ0ofX2Bcu")
                .rol(Rol.OPERADOR)
                .build();

        usuarioRepository.save(operador);

        Mantenimiento mantenimiento1 = Mantenimiento.builder()
                .fechaInicio(LocalDate.now())
                .fechaFinalizacion(LocalDate.now().plusDays(1))
                .asunto("Cambio de aceite")
                .estadoMantenimiento(EstadoMantenimiento.PENDIENTE)
                .operador(operador)
                .vehiculo(vehiculo1)
                .build();

        mantenimientoRepository.save(mantenimiento1);

        // Buscar Mantenimientos por Estado
        List<Mantenimiento> mantenimientosPendientes = mantenimientoRepository.findAllByEstadoMantenimiento(EstadoMantenimiento.PENDIENTE);

        // Verificar que se encuentre el mantenimiento
        assertFalse(mantenimientosPendientes.isEmpty());
        assertEquals(1, mantenimientosPendientes.size());
        assertEquals("Cambio de aceite", mantenimientosPendientes.get(0).getAsunto());
    }

    @Test
    @Transactional
    @Rollback
    void testFindByOperadorId() {
        // Crear y guardar un Vehiculo
        Vehiculo vehiculo1 = Vehiculo.builder()
                .patente("XYZ789")
                .antiguedad(3)
                .kilometraje(0)
                .modelo("Modelo Y")
                .estadoVehiculo(EstadoVehiculo.EN_REPARACION)
                .estadoDeHabilitacion(EstadoDeHabilitacion.HABILITADO)
                .fechaVencimiento(LocalDate.now().plusYears(1))
                .tarjeta(null)
                .build();
        vehiculoRepository.save(vehiculo1);
        // Crear y guardar un Usuario operador
        Usuario operador = Usuario.builder()
                .usuario("operador")
                .contrasena("$2a$10$RRAzywJFxaAG3pRlHXep6u6VNKi5KOTT3M8GCxDPHpAyZ0ofX2Bcu")
                .rol(Rol.OPERADOR)
                .build();
        usuarioRepository.save(operador);

        // Crear y guardar un Mantenimiento
        Mantenimiento mantenimiento1 = Mantenimiento.builder()
                .fechaInicio(LocalDate.now())
                .fechaFinalizacion(LocalDate.now().plusDays(1))
                .asunto("Cambio de aceite")
                .estadoMantenimiento(EstadoMantenimiento.PENDIENTE)
                .operador(operador)
                .vehiculo(vehiculo1)
                .build();

        mantenimientoRepository.save(mantenimiento1);

        // Buscar Mantenimientos por ID de Operador
        List<Mantenimiento> mantenimientos = mantenimientoRepository.findByOperadorId(operador.getId());

        // Verificar que se encuentre el mantenimiento
        assertFalse(mantenimientos.isEmpty());
        assertEquals(1, mantenimientos.size());
        assertEquals("Cambio de aceite", mantenimientos.get(0).getAsunto());
    }

    @Test
    @Transactional
    @Rollback
    void devolverListaDeMantenimientosSegunUnVehiculoID(){
        Vehiculo vehiculo = Vehiculo.builder()
                .patente("XYZ789")
                .antiguedad(3)
                .kilometraje(0)
                .modelo("Modelo Y")
                .estadoVehiculo(EstadoVehiculo.EN_REPARACION)
                .estadoDeHabilitacion(EstadoDeHabilitacion.HABILITADO)
                .fechaVencimiento(LocalDate.now().plusYears(1))
                .tarjeta(null)
                .build();
        vehiculoRepository.save(vehiculo);
        Usuario operador = Usuario.builder()
                .usuario("operador")
                .contrasena("$2a$10$RRAzywJFxaAG3pRlHXep6u6VNKi5KOTT3M8GCxDPHpAyZ0ofX2Bcu")
                .rol(Rol.OPERADOR)
                .build();
        usuarioRepository.save(operador);

        Mantenimiento mantenimiento1 = Mantenimiento.builder()
                .fechaInicio(LocalDate.now())
                .fechaFinalizacion(LocalDate.now().plusDays(1))
                .asunto("Cambio de aceite")
                .estadoMantenimiento(EstadoMantenimiento.PENDIENTE)
                .operador(operador)
                .vehiculo(vehiculo)
                .build();
        Mantenimiento mantenimiento2 = Mantenimiento.builder()
                .fechaInicio(LocalDate.now())
                .fechaFinalizacion(LocalDate.now().plusDays(2))
                .asunto("Cambio de aceite")
                .estadoMantenimiento(EstadoMantenimiento.PENDIENTE)
                .operador(operador)
                .vehiculo(vehiculo)
                .build();

        mantenimientoRepository.saveAll(List.of(mantenimiento1,mantenimiento2));

        List<Mantenimiento> mantenimientosDeUnVehiculo = mantenimientoRepository.findByVehiculoId(1);

        assertEquals(mantenimientosDeUnVehiculo.get(0).getId(),mantenimiento1.getId());
        assertEquals(mantenimientosDeUnVehiculo.get(1).getId(),mantenimiento2.getId());
    }
}
