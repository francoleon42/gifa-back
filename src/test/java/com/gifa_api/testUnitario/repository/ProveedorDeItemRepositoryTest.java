package com.gifa_api.testUnitario.repository;

import com.gifa_api.model.Proveedor;
import com.gifa_api.model.ProveedorDeItem;
import com.gifa_api.repository.IProveedorDeItemRepository;
import com.gifa_api.repository.IProveedorRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import static org.junit.Assert.assertEquals;

@DataJpaTest
public class ProveedorDeItemRepositoryTest {
    @Autowired
    private IProveedorRepository proveedorRepository;

    @Autowired
    private IProveedorDeItemRepository proveedorDeItemRepository;
    private Proveedor proveedor;
    private ProveedorDeItem proveedorDeItem;
    @BeforeEach
    void setUp(){
         proveedor = Proveedor.builder()
                .email("email@gmail.com")
                .nombre("nombre")
                .build();
        proveedorRepository.save(proveedor);
        proveedorDeItem = ProveedorDeItem.builder()
                .proveedor(proveedor)
                .precio(2.2)
                .build();


    }
    @Test
    @Transactional
    @Rollback
    void registrarProveedorDeItem(){
        ProveedorDeItem proveedorDeItemGuardado = proveedorDeItemRepository.save(proveedorDeItem);

        assertEquals(proveedorDeItemGuardado.getId(),proveedorDeItem.getId());
        assertEquals(proveedorDeItemGuardado.getPrecio(),proveedorDeItem.getPrecio());
    }
}
