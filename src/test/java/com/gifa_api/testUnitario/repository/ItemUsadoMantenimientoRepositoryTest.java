package com.gifa_api.testUnitario.repository;

import com.gifa_api.model.ItemUsadoMantenimiento;
import com.gifa_api.repository.IItemUsadoMantenimientoRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
class ItemUsadoMantenimientoRepositoryTest {

    @Autowired
    private IItemUsadoMantenimientoRepository itemUsadoMantenimientoRepository;

    @Test
    @Transactional
    @Rollback
    void testGuardarItemUsadoMantenimientoConNulos() {
        ItemUsadoMantenimiento itemUsadoMantenimiento1 = ItemUsadoMantenimiento.builder()
                .mantenimiento(null)
                .itemDeInventario(null)
                .cantidad(2)
                .build();

        itemUsadoMantenimiento1 = itemUsadoMantenimientoRepository.save(itemUsadoMantenimiento1);

        assertNotNull(itemUsadoMantenimiento1.getId());
    }
}
