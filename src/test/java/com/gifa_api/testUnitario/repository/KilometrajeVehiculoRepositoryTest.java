//package com.gifa_api.testUnitario.repository;
//
//import com.gifa_api.model.KilometrajeVehiculo;
//import com.gifa_api.model.Tarjeta;
//import com.gifa_api.model.Vehiculo;
//import com.gifa_api.repository.IKilometrajeVehiculoRepository;
//import com.gifa_api.repository.IVehiculoRepository;
//import com.gifa_api.utils.enums.EstadoDeHabilitacion;
//import com.gifa_api.utils.enums.EstadoVehiculo;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.transaction.annotation.Transactional;
//import static org.junit.jupiter.api.Assertions.*;
//import java.time.LocalDate;
//import java.time.OffsetDateTime;
//
//@DataJpaTest
//public class KilometrajeVehiculoRepositoryTest {
//    @Autowired
//    private IVehiculoRepository vehiculoRepository;
//
//    @Autowired
//    private IKilometrajeVehiculoRepository kilometrajeVehiculoRepository;
//
//    @Test
//    @Transactional
//    @Rollback
//    void testGuardarKilometrajeVehiculo() {
//        // Crear una Tarjeta
//        Tarjeta tarjeta2 = Tarjeta.builder()
//                .numero(67890) // Número de la tarjeta 2
//                .build();
//
//        Vehiculo vehiculo1 = Vehiculo.builder()
//                .patente("XYZ789")
//                .antiguedad(3)
//                .kilometraje(0)
//                .modelo("Modelo Y")
//                .estadoVehiculo(EstadoVehiculo.EN_REPARACION)
//                .estadoDeHabilitacion(EstadoDeHabilitacion.HABILITADO)
//                .fechaVencimiento(LocalDate.now().plusYears(1))
//                .tarjeta(tarjeta2) // Usar la tarjeta 2
//                .build();
//
//        vehiculo1 = vehiculoRepository.save(vehiculo1);
//
//        KilometrajeVehiculo kilometraje = KilometrajeVehiculo.builder()
//                .vehiculo(vehiculo1)
//                .kilometrosRecorridos(10)
//                .fecha(OffsetDateTime.now())
//                .build();
//
//        kilometraje = kilometrajeVehiculoRepository.save(kilometraje);
//
//        assertNotNull(kilometraje.getId()); // Asegurarse de que se haya generado un ID
//
//        assertEquals(vehiculo1.getId(), kilometraje.getVehiculo().getId()); // Verificar que el vehículo sea el mismo
//    }
//
//}
