package com.gifa_api.testUnitario.repository;

import com.gifa_api.model.KilometrajeVehiculo;
import com.gifa_api.model.Tarjeta;
import com.gifa_api.model.Vehiculo;
import com.gifa_api.repository.IKilometrajeVehiculoRepository;
import com.gifa_api.repository.IVehiculoRepository;
import com.gifa_api.utils.enums.EstadoDeHabilitacion;
import com.gifa_api.utils.enums.EstadoVehiculo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;

@DataJpaTest
public class KilometrajeVehiculoRepositoryTest {
    @Autowired
    private IVehiculoRepository vehiculoRepository;

    @Autowired
    private IKilometrajeVehiculoRepository kilometrajeVehiculoRepository;

    private Vehiculo vehiculo;
    private Tarjeta tarjeta;
    private KilometrajeVehiculo kilometrajeVehiculo;

    @BeforeEach
    void setUp(){
        tarjeta = Tarjeta.builder()
                .numero(67890) // Número de la tarjeta 2
                .build();
        vehiculo =Vehiculo.builder()
                .patente("XYZ789")
                .antiguedad(3)
                .kilometrajeUsado(15)
                .kilometrajeRecorrido(10.0)
                .modelo("Modelo Y")
                .estadoVehiculo(EstadoVehiculo.EN_REPARACION)
                .estadoDeHabilitacion(EstadoDeHabilitacion.HABILITADO)
                .fechaVencimiento(LocalDate.now().plusYears(1))
                .tarjeta(tarjeta) // Usar la tarjeta 2
                .build();
        vehiculoRepository.save(vehiculo);

        kilometrajeVehiculo = KilometrajeVehiculo.builder()
                .vehiculo(vehiculo)
                .kilometrosRecorridos(10)
                .fecha(OffsetDateTime.now())
                .build();
        kilometrajeVehiculoRepository.save(kilometrajeVehiculo);
    }

    @Test
    @Transactional
    @Rollback
    void testGuardarKilometrajeVehiculo() {
        assertNotNull(kilometrajeVehiculo.getId());
        assertEquals(vehiculo.getId(), kilometrajeVehiculo.getVehiculo().getId());
    }

    @Test
    @Transactional
    @Rollback
    void findByIdVehiculo_devuelveKilometrajesRecorridosDeUnVehiculo(){
        List<KilometrajeVehiculo> kilometrajesVehiculoGuardado = kilometrajeVehiculoRepository.findByIdVehiculo(vehiculo.getId());
        assertNotNull(kilometrajesVehiculoGuardado);
        assertEquals(kilometrajesVehiculoGuardado.get(0).getFecha(),kilometrajeVehiculo.getFecha());
        assertEquals(kilometrajesVehiculoGuardado.get(0).getKilometrosRecorridos(),kilometrajeVehiculo.getKilometrosRecorridos());
        assertEquals(kilometrajesVehiculoGuardado.get(0).getVehiculo().getId(),kilometrajeVehiculo.getVehiculo().getId());

    }
}
