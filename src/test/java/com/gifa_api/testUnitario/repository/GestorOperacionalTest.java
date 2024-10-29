package com.gifa_api.testUnitario.repository;

import com.gifa_api.model.GestorOperacional;
import com.gifa_api.repository.IGestorOperacionalRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import static org.junit.Assert.assertEquals;

@DataJpaTest
public class GestorOperacionalTest {

    @Autowired
    private IGestorOperacionalRepository gestorOperacionalRepository;

    private GestorOperacional gestorOperacional;

    @BeforeEach
    void setUp(){
        gestorOperacional = GestorOperacional.builder().presupuesto(10.0).build();

    }

    @Test
    @Transactional
    @Rollback
    void registrarGestorOperacional(){
        GestorOperacional gestorOperacionalGuardado = gestorOperacionalRepository.save(gestorOperacional);
        assertEquals(gestorOperacionalGuardado.getPresupuesto(),gestorOperacional.getPresupuesto());
        assertEquals(gestorOperacionalGuardado.getId().toString(),"1");
    }
}
