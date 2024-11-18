package com.gifa_api.testUnitario.repository;

import com.gifa_api.model.Proveedor;
import com.gifa_api.model.ProveedorDeItem;
import com.gifa_api.repository.IProveedorRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class ProveedroRepositoryTest {
    @Autowired
    private IProveedorRepository proveedorRepository;

    @Test
    @Transactional
    @Rollback
    void guardarProveedor(){
        Proveedor proveedor = Proveedor.builder()
                .id(1)
                .nombre("a")
                .email("pardofede04@gmail.com")
                .proveedorDeItems(null)
                .build();

        Proveedor proveedorGuardado = proveedorRepository.save(proveedor);
            assertEquals(proveedor.getId(),proveedorGuardado.getId());

    }
}
