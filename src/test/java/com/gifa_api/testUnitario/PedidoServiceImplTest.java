package com.gifa_api.testUnitario;

import com.gifa_api.dto.proveedoresYPedidos.PedidoResponseDTO;
import com.gifa_api.exception.NotFoundException;
import com.gifa_api.model.*;
import com.gifa_api.repository.IPedidoRepository;
import com.gifa_api.repository.ItemDeInventarioRepository;
import com.gifa_api.service.IGestorDePedidosService;
import com.gifa_api.service.IProveedorDeItemService;
import com.gifa_api.service.impl.PedidoServiceImpl;
import com.gifa_api.utils.enums.EstadoPedido;
import com.gifa_api.utils.mappers.PedidosMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PedidoServiceImplTest {

    @InjectMocks
    private PedidoServiceImpl pedidoService;

    @Mock
    private IPedidoRepository pedidoRepository;

    @Mock
    private ItemDeInventarioRepository itemDeInventarioRepository;

    @Mock
    private IGestorDePedidosService gestorDePedidosService;

    @Mock
    private IProveedorDeItemService proveedorDeItemService;

    @Mock
    private PedidosMapper pedidosMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreatePedido_ItemExistente() {
        // Arrange
        Integer idItem = 1;
        Integer cantidad = 5;
        String motivo = "Pedido manual";

        ItemDeInventario item = ItemDeInventario.builder().id(idItem).nombre("Item A").build();
        when(itemDeInventarioRepository.findById(idItem)).thenReturn(Optional.of(item));

        Pedido pedido = Pedido.builder()
                .estadoPedido(EstadoPedido.PENDIENTE)
                .item(item)
                .cantidad(cantidad)
                .fecha(LocalDate.now())
                .motivo(motivo)
                .build();

        when(pedidoRepository.save(any(Pedido.class))).thenReturn(pedido);

        // Act
        pedidoService.createPedido(idItem, cantidad, motivo);

        // Assert
        verify(itemDeInventarioRepository, times(1)).findById(idItem);
        verify(pedidoRepository, times(1)).save(any(Pedido.class));
    }

    @Test
    void testCreatePedido_ItemNoExistente() {
        // Arrange
        Integer idItem = 1;
        Integer cantidad = 5;
        String motivo = "Pedido manual";

        when(itemDeInventarioRepository.findById(idItem)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NotFoundException.class, () -> pedidoService.createPedido(idItem, cantidad, motivo));
        verify(itemDeInventarioRepository, times(1)).findById(idItem);
        verify(pedidoRepository, times(0)).save(any(Pedido.class));
    }

    @Test
    void testHacerPedidos() {

        // Arrange
        Integer id1 = 1;
        Integer id2 = 2;
        ItemDeInventario item1 = ItemDeInventario.builder().id(id1).nombre("Item A").stock(4).umbral(5).build();
        ItemDeInventario item2 = ItemDeInventario.builder().id(id2).nombre("Item B").stock(8).umbral(7).build();
        List<ItemDeInventario> items = Arrays.asList(item1, item2);

        GestorDePedidos gestorDePedidos = GestorDePedidos.builder().cantDePedidoAutomatico(3).presupuesto(1000.0).build();
        ProveedorDeItem proveedorEconomico = ProveedorDeItem.builder().precio(50.0).build();

        when(itemDeInventarioRepository.findAll()).thenReturn(items);
        when(gestorDePedidosService.getGestorDePedidos()).thenReturn(gestorDePedidos);
        when(proveedorDeItemService.proveedorMasEconomico(anyInt())).thenReturn(proveedorEconomico);
        when(pedidoRepository.existsByItemId(anyInt())).thenReturn(false);


        pedidoService.hacerPedidos();


        verify(pedidoRepository, times(1)).save(any(Pedido.class)); // Solo se debe crear un pedido
    }

    @Test
    void testHacerPedidos_NoStockBajo() {

        ItemDeInventario item1 = ItemDeInventario.builder().id(1).nombre("Item A").stock(6).umbral(5).build();
        ItemDeInventario item2 = ItemDeInventario.builder().id(2).nombre("Item B").stock(10).umbral(8).build();
        List<ItemDeInventario> items = Arrays.asList(item1, item2);

        when(itemDeInventarioRepository.findAll()).thenReturn(items);


        pedidoService.hacerPedidos();


        verify(pedidoRepository, times(0)).save(any(Pedido.class)); // No debe crear pedidos
    }

    @Test
    void testExisteElPedidoByItemId() {
        // Arrange
        Integer idItem = 1;
        when(pedidoRepository.existsByItemId(idItem)).thenReturn(true);

        // Act
        boolean result = pedidoService.existeElPedidoByItemId(idItem);

        // Assert
        assertTrue(result);
        verify(pedidoRepository, times(1)).existsByItemId(idItem);
    }

    @Test
    void testObtenerPedidos() {
        // Arrange
        Pedido pedido1 = Pedido.builder().id(1).cantidad(10).motivo("Pedido A").build();
        Pedido pedido2 = Pedido.builder().id(2).cantidad(20).motivo("Pedido B").build();

        List<Pedido> pedidosList = Arrays.asList(pedido1, pedido2);
        when(pedidoRepository.findAll()).thenReturn(pedidosList);

        List<PedidoResponseDTO> pedidosDTOs = Arrays.asList(new PedidoResponseDTO(), new PedidoResponseDTO());
        when(pedidosMapper.mapToPedidoDTO(pedidosList)).thenReturn(pedidosDTOs);

        // Act
        List<PedidoResponseDTO> result = pedidoService.obtenerPedidos();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(pedidoRepository, times(1)).findAll();
        verify(pedidosMapper, times(1)).mapToPedidoDTO(pedidosList);
    }
}
