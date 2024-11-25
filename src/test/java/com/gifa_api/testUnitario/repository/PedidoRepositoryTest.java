package com.gifa_api.testUnitario.repository;

import com.gifa_api.model.ItemDeInventario;
import com.gifa_api.model.Pedido;
import com.gifa_api.repository.IPedidoRepository;
import com.gifa_api.repository.ItemDeInventarioRepository;
import com.gifa_api.utils.enums.EstadoPedido;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
@DataJpaTest
public class PedidoRepositoryTest {

    @Autowired
    private IPedidoRepository pedidoRepository;

    @Autowired
    private ItemDeInventarioRepository itemDeInventarioRepository;
    private  Pedido pedido;
    private  Pedido pedido2;
    private  Pedido pedido3;
    private Pedido pedido4;
    private ItemDeInventario itemDeInventario;


    @BeforeEach
    void setUp() {
        itemDeInventario = ItemDeInventario.builder()
                .nombre("Filtro de aceite")
                .umbral(5)
                .stock(10)
                .cantCompraAutomatica(3)
                .build();

        itemDeInventarioRepository.save(itemDeInventario);

        pedido = Pedido.builder()
                .fecha(LocalDate.now())
                .cantidad(5)
                .motivo("Reponer stock")
                .item(itemDeInventario)
                .estadoPedido(EstadoPedido.PENDIENTE)
                .build();
        pedido2 = Pedido.builder()
                .fecha(LocalDate.now())
                .cantidad(5)
                .motivo("Reponer stock")
                .item(itemDeInventario)
                .estadoPedido(EstadoPedido.RECHAZADO)
                .build();

        pedido3 = Pedido.builder()
                .fecha(LocalDate.now())
                .cantidad(5)
                .motivo("Reponer stock")
                .item(itemDeInventario)
                .estadoPedido(EstadoPedido.PRESUPUESTO_INSUFICIENTE)
                .build();

        pedido4 = Pedido.builder()
                .fecha(LocalDate.now())
                .cantidad(5)
                .motivo("Reponer stock")
                .item(itemDeInventario)
                .estadoPedido(EstadoPedido.SIN_PROVEEDOR)
                .build();

        pedidoRepository.saveAll(List.of(pedido,pedido2,pedido3,pedido4));
    }

    @Test
    @Transactional
    @Rollback
    void testGuardarPedido() {
        assertNotNull(pedido.getId()); // Asegurarse de que se haya generado un ID
        assertEquals(5, pedido.getCantidad());
        assertEquals("Reponer stock", pedido.getMotivo());
        assertEquals(EstadoPedido.PENDIENTE, pedido.getEstadoPedido());
    }

    @Test
    @Transactional
    @Rollback
    void testExistsByItemId() {
        assertTrue(pedidoRepository.existsByItemId(itemDeInventario.getId()));
    }

    @Test
    @Transactional
    @Rollback
    void testFindPedidosByEstado() {
        List<Pedido> pedidos = pedidoRepository.findPedidosByEstado(EstadoPedido.PENDIENTE);
        assertEquals(1,pedidos.size());
        assertThat(pedidos.get(0).getEstadoPedido()).isEqualTo(EstadoPedido.PENDIENTE);
    }

    @Test
    @Transactional
    @Rollback
    void testFindPedidosByTresEstados() {
        List<Pedido> pedidos = pedidoRepository.findPedidosByCuatroEstados(EstadoPedido.PENDIENTE,EstadoPedido.RECHAZADO
        ,EstadoPedido.PRESUPUESTO_INSUFICIENTE,EstadoPedido.SIN_PROVEEDOR);
        assertEquals(4,pedidos.size());
        assertThat(pedidos.get(0).getEstadoPedido()).isEqualTo(EstadoPedido.PENDIENTE);
        assertThat(pedidos.get(1).getEstadoPedido()).isEqualTo(EstadoPedido.RECHAZADO);
        assertThat(pedidos.get(2).getEstadoPedido()).isEqualTo(EstadoPedido.PRESUPUESTO_INSUFICIENTE);
        assertThat(pedidos.get(3).getEstadoPedido()).isEqualTo(EstadoPedido.SIN_PROVEEDOR);
    }

}
