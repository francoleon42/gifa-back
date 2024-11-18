package com.gifa_api.testUnitario.repository;

import com.gifa_api.model.Vehiculo;
import com.gifa_api.repository.IVehiculoRepository;
import com.gifa_api.utils.enums.EstadoDeHabilitacion;
import com.gifa_api.utils.enums.EstadoVehiculo;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@DataJpaTest
public class VehiculoRepositoryTest {

    @Autowired
    private IVehiculoRepository vehiculoRepository;

    private Vehiculo vehiculo1;
    private Vehiculo vehiculo2;
    @BeforeEach
    void setUp(){
         vehiculo1 = Vehiculo.builder()
                .id(1)
                .patente("XYZ789")
                .antiguedad(3)
                .kilometrajeUsado(0)
                .kilometrajeRecorrido(0)
                .modelo("Modelo Y")
                .estadoVehiculo(EstadoVehiculo.EN_REPARACION)
                .estadoDeHabilitacion(EstadoDeHabilitacion.HABILITADO)
                .fechaVencimiento(LocalDate.now().plusYears(1))
                .tarjeta(null)
                .build();

        vehiculo2 = Vehiculo.builder()
                .id(2)
                .patente("ABG456")
                .antiguedad(3)
                .kilometrajeUsado(0)
                .kilometrajeRecorrido(0)
                .modelo("Modelo X")
                .estadoVehiculo(EstadoVehiculo.EN_REPARACION)
                .estadoDeHabilitacion(EstadoDeHabilitacion.HABILITADO)
                .fechaVencimiento(LocalDate.now().plusYears(1))
                .tarjeta(null)
                .build();

        vehiculoRepository.saveAll(List.of(vehiculo1,vehiculo2));

    }

    @Test
    @Transactional
    @Rollback
     void creacionDeVehiculo(){
        Vehiculo vehiculo = Vehiculo.builder()
                .patente("EEE789")
                .antiguedad(3)
                .kilometrajeUsado(0)
                .kilometrajeRecorrido(0)
                .modelo("Modelo Y")
                .estadoVehiculo(EstadoVehiculo.EN_REPARACION)
                .estadoDeHabilitacion(EstadoDeHabilitacion.HABILITADO)
                .fechaVencimiento(LocalDate.now().plusYears(1))
                .tarjeta(null)
                .build();

        Vehiculo vehiculoGuardado = vehiculoRepository.save(vehiculo);

        assertEquals(vehiculo.getId(), vehiculoGuardado.getId());
        assertEquals(vehiculo.getPatente(), vehiculoGuardado.getPatente());
        assertEquals(vehiculo.getAntiguedad(), vehiculoGuardado.getAntiguedad());
        assertEquals(vehiculo.getKilometrajeRecorrido(), vehiculoGuardado.getKilometrajeRecorrido());
        assertEquals(vehiculo.getKilometrajeUsado(), vehiculoGuardado.getKilometrajeUsado());
        assertEquals(vehiculo.getModelo(), vehiculoGuardado.getModelo());
        assertEquals(vehiculo.getEstadoVehiculo(), vehiculoGuardado.getEstadoVehiculo());
        assertEquals(vehiculo.getEstadoDeHabilitacion(), vehiculoGuardado.getEstadoDeHabilitacion());
        assertEquals(vehiculo.getFechaVencimiento(), vehiculoGuardado.getFechaVencimiento());
        assertEquals(vehiculo.getTarjeta(), vehiculoGuardado.getTarjeta());

    }

    @Test
    @Transactional
    @Rollback
    void buscarVehiculoPorPatente_devuelveVehiculo(){
        Optional <Vehiculo> vehiculoBuscado = vehiculoRepository.findByPatente(vehiculo1.getPatente());
        assertNotNull(vehiculoBuscado);
        assertEquals(vehiculo1.getQr(),vehiculoBuscado.get().getQr());
        assertEquals(vehiculo1.getKilometrajeUsado(),vehiculoBuscado.get().getKilometrajeUsado());
        assertEquals(vehiculo1.getKilometrajeRecorrido(),vehiculoBuscado.get().getKilometrajeRecorrido());
        assertEquals(vehiculo1.getEstadoVehiculo(),vehiculoBuscado.get().getEstadoVehiculo());
        assertEquals(vehiculo1.getPatente(),vehiculoBuscado.get().getPatente());
        assertEquals(vehiculo1.getAntiguedad(),vehiculoBuscado.get().getAntiguedad());
        assertEquals(vehiculo1.getEstadoDeHabilitacion(),vehiculoBuscado.get().getEstadoDeHabilitacion());
    }


}
