package com.gifa_api.testUnitario.repository;

import com.gifa_api.model.CargaCombustible;
import com.gifa_api.model.ItemDeInventario;
import com.gifa_api.model.Pedido;
import com.gifa_api.model.Tarjeta;
import com.gifa_api.repository.ICargaCombustibleRepository;
import com.gifa_api.repository.IPedidoRepository;
import com.gifa_api.repository.ITarjetaRepository;
import com.gifa_api.utils.enums.EstadoPedido;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class CargaDeCombustibleTest {

    @Autowired
    private ICargaCombustibleRepository cargaCombustibleRepository;

    @Autowired
    private IPedidoRepository pedidoRepository;
    @Autowired
    private ITarjetaRepository tarjetaRepository;

    @Test
    @Transactional
    @Rollback
    void testGuardarCargaCombustible() {
        // Crear y guardar una Tarjeta
        Tarjeta tarjeta1 = Tarjeta.builder()
                .numero(12345)
                .build();
        tarjeta1 = tarjetaRepository.save(tarjeta1); // Guardar la tarjeta

        // Crear una Carga de Combustible
        OffsetDateTime fechaHora = OffsetDateTime.now();
        CargaCombustible cargaCombustible = CargaCombustible.builder()
                .cantidadLitros(50)
                .costoTotal(5000f)
                .fechaHora(fechaHora)
                .precioPorLitro(100f)
                .tarjeta(tarjeta1) // Usar la tarjeta guardada
                .build();

        // Guardar la Carga de Combustible
        cargaCombustible = cargaCombustibleRepository.save(cargaCombustible);

        // Verificar que la Carga de Combustible se haya guardado correctamente
        assertNotNull(cargaCombustible.getId()); // Asegurarse de que se haya generado un ID
        assertEquals(50, cargaCombustible.getCantidadLitros());
        assertEquals(5000f, cargaCombustible.getCostoTotal());
        assertEquals(fechaHora.truncatedTo(ChronoUnit.SECONDS), cargaCombustible.getFechaHora().truncatedTo(ChronoUnit.SECONDS)); // Comparar hasta segundos
        assertEquals(100f, cargaCombustible.getPrecioPorLitro());
        assertEquals(tarjeta1.getNumero(), cargaCombustible.getTarjeta().getNumero()); // Verificar que la tarjeta sea la misma
    }

    @Test
    @Transactional
    @Rollback
    void testFindByNumeroTarjetaAndFechaAfter() {
        Tarjeta tarjeta1 = Tarjeta.builder()
                .numero(12345)
                .build();
        tarjeta1 = tarjetaRepository.save(tarjeta1); // Guardar la tarjeta

        // Crear dos Cargas de Combustible, una para el mismo día y otra para el día anterior
        OffsetDateTime fechaHora1 = OffsetDateTime.now().minusDays(1); // Un día atrás
        CargaCombustible carga1 = CargaCombustible.builder()
                .cantidadLitros(50)
                .fechaHora(fechaHora1)
                .precioPorLitro(100f)
                .tarjeta(tarjeta1) // Usar la tarjeta guardada
                .build();

        OffsetDateTime fechaHora2 = OffsetDateTime.now(); // Hoy
        CargaCombustible carga2 = CargaCombustible.builder()
                .cantidadLitros(60)
                .fechaHora(fechaHora2)
                .precioPorLitro(105f)
                .tarjeta(tarjeta1) // Usar la tarjeta guardada
                .build();

        cargaCombustibleRepository.save(carga1);
        cargaCombustibleRepository.save(carga2);

        List<CargaCombustible> resultados = cargaCombustibleRepository.findByNumeroTarjetaAndFechaAfter(tarjeta1.getNumero(), fechaHora1.minus(1, ChronoUnit.DAYS));

        // Verificar que se obtienen los registros correctos
        assertNotNull(resultados);
        assertEquals(2, resultados.size()); // Ambas cargas deben ser recuperadas
        assertTrue(resultados.stream().anyMatch(c -> c.getCantidadLitros() == 50));
        assertTrue(resultados.stream().anyMatch(c -> c.getCantidadLitros() == 60));
    }

    @Test
    @Transactional
    @Rollback
    void testFindByNumeroTarjetaAndFechaAfterSinResultados() {
        Tarjeta tarjeta1 = Tarjeta.builder()
                .numero(12345)
                .build();
        tarjeta1 = tarjetaRepository.save(tarjeta1); // Guardar la tarjeta

        List<CargaCombustible> resultados = cargaCombustibleRepository.findByNumeroTarjetaAndFechaAfter(tarjeta1.getNumero(), OffsetDateTime.now());

        assertNotNull(resultados);
        assertTrue(resultados.isEmpty());
    }
}
