package com.gifa_api.testUnitario.repository;

import com.gifa_api.model.Proveedor;
import com.gifa_api.repository.IProveedorRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import static org.junit.Assert.assertEquals;

@DataJpaTest
public class ProveedorRepositoryTest {
    @Autowired
    private IProveedorRepository proveedorRepository;

    @Test
    @Transactional
    @Rollback
    void registrarProveedor(){
        Proveedor proveedor = Proveedor.builder()
                .email("email@gmail.com")
                .nombre("nombre")
                .build();

        Proveedor proveedorGuardado = proveedorRepository.save(proveedor);

        assertEquals(proveedorGuardado.getId(),proveedor.getId());
        assertEquals(proveedorGuardado.getEmail(),proveedor.getEmail());

        assertEquals(proveedorGuardado.getNombre(),proveedor.getNombre());

    }

}
