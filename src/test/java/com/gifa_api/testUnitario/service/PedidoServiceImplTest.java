package com.gifa_api.testUnitario.service;

import com.gifa_api.dto.pedido.CrearPedidoDTO;
import com.gifa_api.dto.pedido.PedidoResponseDTO;
import com.gifa_api.exception.NotFoundException;
import com.gifa_api.model.*;
import com.gifa_api.repository.IPedidoRepository;
import com.gifa_api.repository.ItemDeInventarioRepository;
import com.gifa_api.service.IGestorOperacionalService;
import com.gifa_api.service.IProveedorDeItemService;
import com.gifa_api.service.impl.PedidoServiceImpl;
import com.gifa_api.utils.mappers.PedidosMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PedidoServiceImplTest {

    @Mock
    private IPedidoRepository pedidoRepository;

    @Mock
    private ItemDeInventarioRepository itemDeInventarioRepository;

    @Mock
    private IProveedorDeItemService proveedorDeItemService;

    @Mock
    private PedidosMapper pedidosMapper;

    @Mock
    private IGestorOperacionalService gestorOperacionalService;

    @InjectMocks
    private PedidoServiceImpl pedidoService;

    private ItemDeInventario itemDeInventario;

    private ProveedorDeItem proveedorDeItemMasEconomico;

    private GestorOperacional gestor;

    private CrearPedidoDTO pedidoDTO;

    @BeforeEach
    void setUp(){
        itemDeInventario = ItemDeInventario.builder()
                .id(1)
                .umbral(5)
                .stock(4)
                .cantCompraAutomatica(5)
                .nombre("Item A")
                .build();

        gestor = GestorOperacional
                .builder()
                .id(1)
                .presupuesto(11.0)
                .build();
        proveedorDeItemMasEconomico = ProveedorDeItem
                .builder()
                .id(1)
                .precio(1.0)
                .proveedor(new Proveedor())
                .itemDeInventario(itemDeInventario)
                .build();

        pedidoDTO = CrearPedidoDTO.builder()
                .idItem(1)
                .cantidad(1)
                .motivo("motivo")
                .build();
    }

    @Test
    void testCrearPedido_ItemNoExistente() {
        when(itemDeInventarioRepository.findById(pedidoDTO.getIdItem())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> pedidoService.createPedido(pedidoDTO));
        verify(pedidoRepository,never()).save(any(Pedido.class));
    }

    @Test
    void testHacerPedidos_lanzaNotFoundException() {
        Integer idItem = 1;
        when(itemDeInventarioRepository.findById(idItem)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> pedidoService.hacerPedidos(1));
        verify(pedidoRepository,never()).save(any(Pedido.class));
    }

    @Test
    void cantidadNoPuedeSerNull(){
        pedidoDTO.setCantidad(null);
        assertThrows(IllegalArgumentException.class, () -> pedidoService.createPedido(pedidoDTO));
        verify(pedidoRepository,never()).save(any(Pedido.class));

    }

    @Test
    void cantidadTieneQueSerPositiva(){
        pedidoDTO.setCantidad(0);
        assertThrows(IllegalArgumentException.class, () -> pedidoService.createPedido(pedidoDTO));
        verify(pedidoRepository,never()).save(any(Pedido.class));

    }

    @Test
    void motivoNoPuedeSerNull(){
        pedidoDTO.setMotivo(null);
        assertThrows(IllegalArgumentException.class, () -> pedidoService.createPedido(pedidoDTO));
        verify(pedidoRepository,never()).save(any(Pedido.class));
    }

    @Test
    void motivoNoPuedeSerVacio(){
        pedidoDTO.setMotivo("");
        assertThrows(IllegalArgumentException.class, () -> pedidoService.createPedido(pedidoDTO));
        verify(pedidoRepository,never()).save(any(Pedido.class));
    }

    @Test
    void testCrearPedido_ItemExistente() {
        when(itemDeInventarioRepository.findById(pedidoDTO.getIdItem())).thenReturn(Optional.of(itemDeInventario));

        pedidoService.createPedido(pedidoDTO);

        verify(itemDeInventarioRepository, times(1)).findById(pedidoDTO.getIdItem());
        verify(pedidoRepository, times(1)).save(any(Pedido.class));
    }


    @Test
    void testHacerPedidos_DatosValidos() {
        when(itemDeInventarioRepository.findById(itemDeInventario.getId())).thenReturn(Optional.of(itemDeInventario));
        when(gestorOperacionalService.getGestorOperacional()).thenReturn(gestor);
        when(proveedorDeItemService.proveedorMasEconomico(itemDeInventario.getId())).thenReturn(proveedorDeItemMasEconomico);

        pedidoService.hacerPedidos(itemDeInventario.getId());

        verify(pedidoRepository, times(1)).save(any(Pedido.class));
    }

    @Test
    void testHacerPedidos_StockMayorQueUmbral() {
        itemDeInventario.setUmbral(6);
        when(itemDeInventarioRepository.findById(itemDeInventario.getId())).thenReturn(Optional.of(itemDeInventario));
        when(gestorOperacionalService.getGestorOperacional()).thenReturn(gestor);
        when(proveedorDeItemService.proveedorMasEconomico(itemDeInventario.getId())).thenReturn(proveedorDeItemMasEconomico);

        pedidoService.hacerPedidos(itemDeInventario.getId());

        verify(pedidoRepository, never()).save(any(Pedido.class));
    }

    @Test
    void testHacerPedidos_PresupuestoInvalido() {
        gestor.setPresupuesto(9.0);
        when(itemDeInventarioRepository.findById(itemDeInventario.getId())).thenReturn(Optional.of(itemDeInventario));
        when(gestorOperacionalService.getGestorOperacional()).thenReturn(gestor);
        when(proveedorDeItemService.proveedorMasEconomico(itemDeInventario.getId())).thenReturn(proveedorDeItemMasEconomico);

        pedidoService.hacerPedidos(itemDeInventario.getId());

        verify(pedidoRepository, never()).save(any(Pedido.class));
    }

    @Test
    void testObtenerPedidos() {
        Pedido pedido1 = Pedido.builder().id(1).cantidad(10).motivo("Pedido A").build();
        Pedido pedido2 = Pedido.builder().id(2).cantidad(20).motivo("Pedido B").build();

        List<Pedido> pedidosList = Arrays.asList(pedido1, pedido2);
        when(pedidoRepository.findAll()).thenReturn(pedidosList);

        List<PedidoResponseDTO> pedidosDTOs = Arrays.asList(new PedidoResponseDTO(), new PedidoResponseDTO());
        when(pedidosMapper.mapToPedidoDTO(pedidosList)).thenReturn(pedidosDTOs);

        List<PedidoResponseDTO> result = pedidoService.obtenerPedidos();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(pedidoRepository, times(1)).findAll();
        verify(pedidosMapper, times(1)).mapToPedidoDTO(pedidosList);
    }
}
