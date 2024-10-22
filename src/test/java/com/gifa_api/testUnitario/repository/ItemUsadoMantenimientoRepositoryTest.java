package com.gifa_api.testUnitario.repository;

import com.gifa_api.model.ItemUsadoMantenimiento;
import com.gifa_api.repository.IItemUsadoMantenimientoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
class ItemUsadoMantenimientoRepositoryTest {

    @Autowired
    private IItemUsadoMantenimientoRepository itemUsadoMantenimientoRepository;

    @Test
    void testGuardarItemUsadoMantenimientoConNulos() {
        // Crear un ItemUsadoMantenimiento con mantenimiento y itemDeInventario nulos
        ItemUsadoMantenimiento itemUsadoMantenimiento1 = ItemUsadoMantenimiento.builder()
                .mantenimiento(null)
                .itemDeInventario(null)
                .cantidad(2)
                .build();

        // Guardar el ItemUsadoMantenimiento
        itemUsadoMantenimiento1 = itemUsadoMantenimientoRepository.save(itemUsadoMantenimiento1);

        // Verificar que el ItemUsadoMantenimiento se haya guardado correctamente
        assertNotNull(itemUsadoMantenimiento1.getId());
        // Agregar más aserciones según sea necesario
    }
}
