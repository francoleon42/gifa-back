package com.gifa_api.testUnitario.repository;

import com.gifa_api.model.Pedido;
import com.gifa_api.model.ItemDeInventario;
import com.gifa_api.repository.IPedidoRepository;
import com.gifa_api.repository.ItemDeInventarioRepository; // Asegúrate de importar el repositorio correcto
import com.gifa_api.utils.enums.EstadoPedido;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class GestorDePedidoRepositoryTest {

    @Autowired
    private IPedidoRepository pedidoRepository;

    @Autowired
    private ItemDeInventarioRepository itemRepository; // Cambiado para usar el repositorio correcto

    @Test
    @Transactional
    @Rollback
    void guardarPedido() {
        // Crear el ItemDeInventario que será asociado al Pedido
        ItemDeInventario item1 = ItemDeInventario.builder()
                .nombre("Filtro de aceite")
                .umbral(5)
                .stock(10)
                .cantCompraAutomatica(3)
                .build();

        // Guardar el ItemDeInventario primero
        ItemDeInventario itemGuardado = itemRepository.save(item1);

        // Crear y guardar el Pedido asociado al ItemDeInventario
        Pedido pedido = Pedido.builder()
                .fecha(LocalDate.now())
                .cantidad(5)
                .motivo("Reponer stock")
                .item(itemGuardado) // Asociar el item guardado
                .estadoPedido(EstadoPedido.PENDIENTE)
                .build();

        Pedido pedidoGuardado = pedidoRepository.save(pedido);

        // Verificar que el Pedido fue guardado correctamente
        assertNotNull(pedidoGuardado);
        assertNotNull(pedidoGuardado.getId()); // Asegurarse que el ID fue generado
        assertEquals(5, pedidoGuardado.getCantidad());
        assertEquals("Reponer stock", pedidoGuardado.getMotivo());
        assertEquals(EstadoPedido.PENDIENTE, pedidoGuardado.getEstadoPedido());
        assertEquals(itemGuardado, pedidoGuardado.getItem()); // Verificar que el ItemDeInventario asociado es correcto
    }
}
