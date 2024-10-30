package com.gifa_api.testUnitario.repository;

import com.gifa_api.model.ItemDeInventario;
import com.gifa_api.repository.ItemDeInventarioRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class ItemDeInventarioTest {
    @Autowired
    private ItemDeInventarioRepository itemDeInventarioRepository;

//    @Test
//    @Transactional
//    @Rollback
//    void creacionDeItemDeInventario(){
//        ItemDeInventario item = ItemDeInventario.builder()
//                .id(1)
//                .nombre("Filtro de aceite")
//                .umbral(5)
//                .stock(10)
//                .cantCompraAutomatica(3)
//                .build();
//
//        ItemDeInventario itemGuardado = itemDeInventarioRepository.save(item);
//
//        assertEquals(item.getId(),itemGuardado.getId());
//        assertEquals(item.getNombre(),itemGuardado.getNombre());
//        assertEquals(item.getUmbral(),itemGuardado.getUmbral());
//        assertEquals(item.getStock(),itemGuardado.getStock());
//        assertEquals(item.getCantCompraAutomatica(),itemGuardado.getCantCompraAutomatica());
//    }
}
