package com.gifa_api.testUnitario.repository;

import com.gifa_api.model.Vehiculo;
import com.gifa_api.repository.IVehiculoRepository;
import com.gifa_api.utils.enums.EstadoDeHabilitacion;
import com.gifa_api.utils.enums.EstadoVehiculo;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

@DataJpaTest
public class VehiculoRepositoryTest {

    @Autowired
    private IVehiculoRepository vehiculoRepository;

//    @Test
//    @Transactional
//    @Rollback
//     void creacionDeVehiculo(){
//        Vehiculo vehiculo = Vehiculo.builder()
//                .id(1)
//                .patente("XYZ789")
//                .antiguedad(3)
//                .kilometraje(0)
//                .modelo("Modelo Y")
//                .estadoVehiculo(EstadoVehiculo.EN_REPARACION)
//                .estadoDeHabilitacion(EstadoDeHabilitacion.HABILITADO)
//                .fechaVencimiento(LocalDate.now().plusYears(1))
//                .tarjeta(null)
//                .build();
//
//        Vehiculo vehiculoGuardado = vehiculoRepository.save(vehiculo);
//
//        assertEquals(vehiculo.getId(), vehiculoGuardado.getId());
//        assertEquals(vehiculo.getPatente(), vehiculoGuardado.getPatente());
//        assertEquals(vehiculo.getAntiguedad(), vehiculoGuardado.getAntiguedad());
//        assertEquals(vehiculo.getKilometraje(), vehiculoGuardado.getKilometraje());
//        assertEquals(vehiculo.getModelo(), vehiculoGuardado.getModelo());
//        assertEquals(vehiculo.getEstadoVehiculo(), vehiculoGuardado.getEstadoVehiculo());
//        assertEquals(vehiculo.getEstadoDeHabilitacion(), vehiculoGuardado.getEstadoDeHabilitacion());
//        assertEquals(vehiculo.getFechaVencimiento(), vehiculoGuardado.getFechaVencimiento());
//        assertEquals(vehiculo.getTarjeta(), vehiculoGuardado.getTarjeta());
//
//    }
}
