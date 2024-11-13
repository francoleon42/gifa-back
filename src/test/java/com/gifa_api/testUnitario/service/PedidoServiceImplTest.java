package com.gifa_api.testUnitario.service;

import com.gifa_api.dto.pedido.CrearPedidoDTO;
import com.gifa_api.dto.pedido.PedidoResponseDTO;
import com.gifa_api.exception.BadRequestException;
import com.gifa_api.exception.NotFoundException;
import com.gifa_api.model.*;
import com.gifa_api.repository.IPedidoRepository;
import com.gifa_api.repository.ItemDeInventarioRepository;
import com.gifa_api.service.IGestorOperacionalService;
import com.gifa_api.service.IProveedorDeItemService;
import com.gifa_api.service.impl.PedidoServiceImpl;
import com.gifa_api.utils.enums.EstadoPedido;
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

    private Pedido pedido;

    @BeforeEach
    void setUp(){
        itemDeInventario = ItemDeInventario.builder()
                .id(1)
                .umbral(3)
                .stock(5)
                .cantCompraAutomatica(1)
                .nombre("Item A")
                .build();
        pedido = Pedido.
                builder().
                estadoPedido(EstadoPedido.ACEPTADO).
                id(1).cantidad(10).motivo("Pedido A")
                .item(itemDeInventario)
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
    void testCreatePedido_ItemNoExistente() {
        when(itemDeInventarioRepository.findById(pedidoDTO.getIdItem())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> pedidoService.crearPedidoManual(pedidoDTO));
        verify(pedidoRepository,never()).save(any(Pedido.class));
    }

    @Test
    void testHacerPedidoAutomatico_lanzaNotFoundException() {
        Integer idItem = 1;
        when(itemDeInventarioRepository.findById(idItem)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> pedidoService.hacerPedidoAutomatico(1));
        verify(pedidoRepository,never()).save(any(Pedido.class));
    }

    @Test
    void cantidadTieneQueSerPositiva(){
        pedidoDTO.setCantidad(0);
        verificarNoRegistroDePedido();
    }
    @Test
    void cantidadNoPuedeSerNull(){
        pedidoDTO.setCantidad(null);
        verificarNoRegistroDePedido();
    }

    @Test
    void motivoNoPuedeSerNull(){
        pedidoDTO.setMotivo(null);
        verificarNoRegistroDePedido();
    }

    @Test
    void motivoNoPuedeSerVacio(){
        pedidoDTO.setMotivo("");
        verificarNoRegistroDePedido();
    }

    @Test
    void confirmarPedidoRecibidoConPedidoInvalido(){
        when(pedidoRepository.findById(pedido.getId())).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class,() ->pedidoService.confirmarPedidoRecibido(pedido.getId()));
        verify(pedidoRepository,never()).save(any(Pedido.class));
    }

    @Test
    void confirmarPedidoQueNoEstaAceptado(){
        pedido.setEstadoPedido(EstadoPedido.RECHAZADO);
        when(pedidoRepository.findById(pedido.getId())).thenReturn(Optional.of(pedido));
        assertThrows(RuntimeException.class, () -> pedidoService.confirmarPedidoRecibido(pedido.getId()));

    }

    @Test
    void testHacerPedidoAutomatico_PresupuestoInvalido() {
        itemDeInventario.setUmbral(10);
        gestor.setPresupuesto(0.0);
        when(itemDeInventarioRepository.findById(itemDeInventario.getId())).thenReturn(Optional.of(itemDeInventario));
        when(gestorOperacionalService.getGestorOperacional()).thenReturn(gestor);
        when(proveedorDeItemService.proveedorMasEconomico(itemDeInventario.getId())).thenReturn(proveedorDeItemMasEconomico);

        pedidoService.hacerPedidoAutomatico(itemDeInventario.getId());

        verify(pedidoRepository, times(1)).save(any(Pedido.class));
    }

    @Test
    void testCrearPedido_ItemExistente() {
        when(itemDeInventarioRepository.findById(pedidoDTO.getIdItem())).thenReturn(Optional.of(itemDeInventario));
        when(proveedorDeItemService.proveedorMasEconomico(pedidoDTO.getIdItem())).thenReturn(proveedorDeItemMasEconomico);
        when(gestorOperacionalService.getGestorOperacional()).thenReturn(gestor);
        pedidoService.crearPedidoManual(pedidoDTO);

        verify(itemDeInventarioRepository, times(1)).findById(pedidoDTO.getIdItem());
        verify(pedidoRepository, times(1)).save(any(Pedido.class));
        verify(gestorOperacionalService,times(1)).getGestorOperacional();
        verify(proveedorDeItemService,times(1)).proveedorMasEconomico(itemDeInventario.getId());
    }


    @Test
    void testHacerPedidoAutomatico_DatosValidos() {
        itemDeInventario.setUmbral(6);
        when(itemDeInventarioRepository.findById(itemDeInventario.getId())).thenReturn(Optional.of(itemDeInventario));
        when(gestorOperacionalService.getGestorOperacional()).thenReturn(gestor);
        when(proveedorDeItemService.proveedorMasEconomico(itemDeInventario.getId())).thenReturn(proveedorDeItemMasEconomico);

        pedidoService.hacerPedidoAutomatico(itemDeInventario.getId());

        verify(pedidoRepository, times(1)).save(any(Pedido.class));

    }

    @Test
    void testObtenerPedidos() {
        List<Pedido> pedidosList = Arrays.asList(pedido);
        when(pedidoRepository.findAll()).thenReturn(pedidosList);

        List<PedidoResponseDTO> pedidosDTOs = Arrays.asList(new PedidoResponseDTO(), new PedidoResponseDTO());
        when(pedidosMapper.mapToPedidoDTO(pedidosList)).thenReturn(pedidosDTOs);

        List<PedidoResponseDTO> result = pedidoService.obtenerPedidos();

        assertEquals(2, result.size());
        verify(pedidoRepository, times(1)).findAll();
        verify(pedidosMapper, times(1)).mapToPedidoDTO(pedidosList);
    }

    @Test
    void testObtenerPedidosAceptados() {
        List<PedidoResponseDTO> pedidosResponseDTO = List.of(
                PedidoResponseDTO.builder().estadoPedido("ACEPTADO").build()
        );
        when(pedidosMapper.mapToPedidoDTO(pedidoRepository.findPedidosByEstado(EstadoPedido.ACEPTADO))).thenReturn(pedidosResponseDTO);

        List<PedidoResponseDTO> result = pedidoService.obtenerPedidos();

        assertEquals(1, result.size());
        verify(pedidoRepository, times(1)).findAll();
        verify(pedidosMapper, times(1)).mapToPedidoDTO(pedidoRepository.findPedidosByEstado(EstadoPedido.ACEPTADO));
    }

    @Test
    void testObtenerRechazadosYpendientes() {
        List<PedidoResponseDTO> pedidosResponseDTO = List.of(
                PedidoResponseDTO.builder().estadoPedido("Rechazado").build(),
                PedidoResponseDTO.builder().estadoPedido("Pendiente").build()
        );
        when(pedidosMapper.mapToPedidoDTO(pedidoRepository.findPedidosByTresEstados(EstadoPedido.PENDIENTE,EstadoPedido.RECHAZADO,EstadoPedido.PRESUPUESTO_INSUFICIENTE))).thenReturn(pedidosResponseDTO);

        List<PedidoResponseDTO> result = pedidoService.obtenerPedidos();

        assertEquals(2, result.size());
        verify(pedidoRepository, times(1)).findAll();
        verify(pedidosMapper, times(1)).mapToPedidoDTO(pedidoRepository.findPedidosByTresEstados(EstadoPedido.PENDIENTE,EstadoPedido.RECHAZADO,EstadoPedido.PRESUPUESTO_INSUFICIENTE));
    }

    @Test
    void confirmarPedidoRecibidoExitosamente(){
        when(pedidoRepository.findById(pedido.getId())).thenReturn(Optional.of(pedido));
        pedidoService.confirmarPedidoRecibido(pedido.getId());

        assertEquals(pedido.getItem().getStock(),15);
        assertEquals(pedido.getEstadoPedido(),EstadoPedido.FINALIZADO);
        verify(pedidoRepository,times(1)).save(any(Pedido.class));
    }

    public void  verificarNoRegistroDePedido(){
        assertThrows(BadRequestException.class, () -> pedidoService.crearPedidoManual(pedidoDTO));
        verify(pedidoRepository,never()).save(any(Pedido.class));
    }
}