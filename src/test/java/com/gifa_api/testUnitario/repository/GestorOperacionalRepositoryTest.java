package com.gifa_api.testUnitario.repository;

import com.gifa_api.model.GestorOperacional;
import com.gifa_api.repository.IGestorOperacionalRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class GestorOperacionalRepositoryTest {
    @Autowired
    private IGestorOperacionalRepository gestorRepository;

    @Test
    @Transactional
    @Rollback
    void guardarGestorOperacional() {
        GestorOperacional gestor = GestorOperacional.builder()
                .id(1)
                .presupuesto(300.0)
                .build();
        GestorOperacional gestorGuardado = gestorRepository.save(gestor);
        assertEquals(gestor.getId(), gestorGuardado.getId());
        assertEquals(gestor.getPresupuesto(), gestorGuardado.getPresupuesto());

    }
}
