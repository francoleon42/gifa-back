package com.gifa_api.testUnitario.repository;

import com.gifa_api.model.KilometrajeVehiculo;
import com.gifa_api.model.Tarjeta;
import com.gifa_api.model.Vehiculo;
import com.gifa_api.repository.IKilometrajeVehiculoRepository;
import com.gifa_api.repository.IVehiculoRepository;
import com.gifa_api.utils.enums.EstadoDeHabilitacion;
import com.gifa_api.utils.enums.EstadoVehiculo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;

@DataJpaTest
public class KilometrajeVehiculoRepositoryTest {
    @Autowired
    private IVehiculoRepository vehiculoRepository;

    @Autowired
    private IKilometrajeVehiculoRepository kilometrajeVehiculoRepository;

    @Test
    @Transactional
    @Rollback
    void testGuardarKilometrajeVehiculo() {
        // Crear una Tarjeta
        Tarjeta tarjeta2 = Tarjeta.builder()
                .numero(67890) // Número de la tarjeta 2
                .build();

        Vehiculo vehiculo1 = Vehiculo.builder()
                .patente("XYZ789")
                .antiguedad(3)
                .kilometraje(0)
                .litrosDeTanque(50)
                .modelo("Modelo Y")
                .estadoVehiculo(EstadoVehiculo.EN_REPARACION)
                .estadoDeHabilitacion(EstadoDeHabilitacion.HABILITADO)
                .fechaVencimiento(LocalDate.now().plusYears(1))
                .tarjeta(tarjeta2) // Usar la tarjeta 2
                .build();

        vehiculo1 = vehiculoRepository.save(vehiculo1);

        KilometrajeVehiculo kilometraje = KilometrajeVehiculo.builder()
                .kilometrosRecorridos(100 * 1f) // Usar un valor fijo para i
                .kilometroAlFinTrayecto(1000 + (100 * 1f)) // Usar un valor fijo para i
                .kilometroInicioTrayecto(900 + (100 * 1f)) // Usar un valor fijo para i
                .fechaInicio(LocalDate.now().minusDays(1)) // Usar un valor fijo para i
                .fechaFin(LocalDate.now())
                .vehiculo(vehiculo1) // Usar el vehículo guardado
                .build();

        kilometraje = kilometrajeVehiculoRepository.save(kilometraje);

        assertNotNull(kilometraje.getId()); // Asegurarse de que se haya generado un ID
        assertEquals(100f, kilometraje.getKilometrosRecorridos());
        assertEquals(1100f, kilometraje.getKilometroAlFinTrayecto());
        assertEquals(1000f, kilometraje.getKilometroInicioTrayecto());
        assertEquals(LocalDate.now().minusDays(1), kilometraje.getFechaInicio());
        assertEquals(LocalDate.now(), kilometraje.getFechaFin());
        assertEquals(vehiculo1.getId(), kilometraje.getVehiculo().getId()); // Verificar que el vehículo sea el mismo
    }

}
