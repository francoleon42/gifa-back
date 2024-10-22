//package com.gifa_api.testUnitario.controller;
//
//import com.gifa_api.controller.ProveedoresYPedidosController;
//import com.gifa_api.dto.proveedoresYPedidos.*;
//import com.gifa_api.service.IGestorDePedidosService;
//import com.gifa_api.service.IPedidoService;
//import com.gifa_api.service.IProvedorService;
//import com.gifa_api.service.IProveedorDeItemService;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//
//import java.util.Collections;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//class ProveedoresYPedidosControllerTest {
//
//    @InjectMocks
//    private ProveedoresYPedidosController proveedoresYPedidosController;
//
//    @Mock
//    private IPedidoService pedidoService;
//
//    @Mock
//    private IProvedorService provedorService;
//
//    @Mock
//    private IProveedorDeItemService proveedorDeItemService;
//
//    @Mock
//    private IGestorDePedidosService gestorDePedidosService;
//
//    @Test
//    void testRegistrarProveedor() {
//        RegistroProveedorRequestDTO requestDTO = new RegistroProveedorRequestDTO();
//        // Configura requestDTO según sea necesario
//
//        ResponseEntity<?> response = proveedoresYPedidosController.registrarProveedor(requestDTO);
//
//        verify(provedorService, times(1)).registrarProveedor(requestDTO);
//        assertEquals(HttpStatus.CREATED, response.getStatusCode());
//    }
//
//    @Test
//    void testAsociarProveedorAItem() {
//        AsociacionProveedorDeITemDTO requestDTO = new AsociacionProveedorDeITemDTO();
//        // Configura requestDTO según sea necesario
//
//        ResponseEntity<?> response = proveedoresYPedidosController.asociarProveedorAItem(requestDTO);
//
//        verify(proveedorDeItemService, times(1)).asociarProveedorAItem(requestDTO);
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//    }
//
//    @Test
//    void testObtenerGestorDePedidos() {
//        GestorDePedidosDTO gestorDTO = new GestorDePedidosDTO();
//        when(gestorDePedidosService.obtenerGestorDePedidos()).thenReturn(gestorDTO);
//
//        ResponseEntity<GestorDePedidosDTO> response = proveedoresYPedidosController.obtenerGestorDePedidos();
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(gestorDTO, response.getBody());
//    }
//
//    @Test
//    void testActualizarGestorDePedidos() {
//        GestorDePedidosDTO requestDTO = new GestorDePedidosDTO();
//        // Configura requestDTO según sea necesario
//
//        ResponseEntity<?> response = proveedoresYPedidosController.actualizarGestorDePedidos(requestDTO);
//
//        verify(gestorDePedidosService, times(1)).actualizarGestorDePedidos(requestDTO);
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//    }
//
//    @Test
//    void testVerPedidos() {
//        List<PedidoResponseDTO> pedidos = Collections.emptyList();
//        when(pedidoService.obtenerPedidos()).thenReturn(pedidos);
//
//        ResponseEntity<List<PedidoResponseDTO>> response = proveedoresYPedidosController.verPedidos();
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(pedidos, response.getBody());
//    }
//
//    @Test
//    void testGenerarPedido() {
//        PedidoManualDTO pedidoManualDTO = new PedidoManualDTO();
//
//        ResponseEntity<?> response = proveedoresYPedidosController.generarPedido(pedidoManualDTO);
//
//        verify(pedidoService, times(1)).createPedido(pedidoManualDTO.getIdItem(), pedidoManualDTO.getCantidad(), pedidoManualDTO.getMotivo());
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//    }
//}
