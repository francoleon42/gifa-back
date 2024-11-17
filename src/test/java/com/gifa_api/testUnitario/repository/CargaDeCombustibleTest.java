package com.gifa_api.testUnitario.repository;

import com.gifa_api.model.CargaCombustible;

import com.gifa_api.model.Tarjeta;
import com.gifa_api.repository.ICargaCombustibleRepository;
import com.gifa_api.repository.IPedidoRepository;
import com.gifa_api.repository.ITarjetaRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDate;
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
        Tarjeta tarjeta1 = Tarjeta.builder()
                .numero(12345)
                .build();
        tarjeta1 = tarjetaRepository.save(tarjeta1);

        CargaCombustible cargaCombustible = CargaCombustible.builder()
                .cantidadLitros(50)
                .costoTotal(5000f)
                .precioPorLitro(100f)
                .tarjeta(tarjeta1)
                .build();

        // Guardar la Carga de Combustible
        cargaCombustible = cargaCombustibleRepository.save(cargaCombustible);

        assertNotNull(cargaCombustible.getId());
        assertEquals(50, cargaCombustible.getCantidadLitros());
        assertEquals(5000f, cargaCombustible.getCostoTotal());
        assertEquals(100f, cargaCombustible.getPrecioPorLitro());
        assertEquals(tarjeta1.getNumero(), cargaCombustible.getTarjeta().getNumero());
    }

    @Test
    @Transactional
    @Rollback()
    void findByNumeroTarjetaAndFechaBetween() {
        Tarjeta tarjeta = Tarjeta.builder()
                .numero(12345)
                .build();
        tarjeta = tarjetaRepository.save(tarjeta);

        CargaCombustible carga1 = CargaCombustible.builder()
                .tarjeta(tarjeta)
                .cantidadLitros(50)
                .fechaHora(LocalDate.now())
                .precioPorLitro(100f)
                .tarjeta(tarjeta)
                .build();

        CargaCombustible carga2 = CargaCombustible.builder()
                .cantidadLitros(60)
                .tarjeta(tarjeta)
                .fechaHora(LocalDate.now().minusDays(1))
                .precioPorLitro(105f)
                .tarjeta(tarjeta)
                .build();

        cargaCombustibleRepository.save(carga1);
        cargaCombustibleRepository.save(carga2);

        List<CargaCombustible> resultados = cargaCombustibleRepository.findByNumeroTarjetaAndFechaBetween(tarjeta.getNumero()
                ,LocalDate.now().minusDays(2),LocalDate.now().plusDays(1));

        assertNotNull(resultados);
        assertEquals(2, resultados.size());
        assertTrue(resultados.stream().anyMatch(c -> c.getPrecioPorLitro() == 100f));
        assertTrue(resultados.stream().anyMatch(c -> c.getPrecioPorLitro() == 105f));
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
        tarjeta1 = tarjetaRepository.save(tarjeta1);

        List<CargaCombustible> resultados = cargaCombustibleRepository.findByNumeroTarjetaAndFechaBetween(tarjeta1.getNumero(), LocalDate.now(),LocalDate.now().plusDays(1));

        assertNotNull(resultados);
        assertTrue(resultados.isEmpty());
    }
}
