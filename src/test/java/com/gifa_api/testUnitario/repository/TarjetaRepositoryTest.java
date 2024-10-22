package com.gifa_api.testUnitario.repository;

import com.gifa_api.model.Tarjeta;
import com.gifa_api.repository.ITarjetaRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class TarjetaRepositoryTest {

    @Autowired
    private ITarjetaRepository tarjetaRepository;

    @Test
    @Transactional
    @Rollback
    void guardarTarjeta(){
        Tarjeta tarjeta = Tarjeta.builder()
                .numero(12345)
                .build();

        Tarjeta tarjetaGuardada = tarjetaRepository.save(tarjeta);

        assertEquals(tarjeta,tarjetaGuardada);

    }
}
