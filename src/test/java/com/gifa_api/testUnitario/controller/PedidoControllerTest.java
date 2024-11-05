//package com.gifa_api.testUnitario.controller;
//
//import com.gifa_api.controller.PedidoController;
//import com.gifa_api.dto.pedido.CrearPedidoDTO;
//import com.gifa_api.dto.pedido.PedidoResponseDTO;
//import com.gifa_api.service.IPedidoService;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.http.HttpStatus;
//
//import java.util.List;
//
//import static org.mockito.Mockito.doNothing;
//import static org.mockito.Mockito.when;
//import static org.junit.jupiter.api.Assertions.*;
//
//@ExtendWith(MockitoExtension.class)
//public class PedidoControllerTest {
//    @Mock
//    private IPedidoService pedidoService;
//
//    @InjectMocks
//    private PedidoController pedidoController;
//
//    @Test
//    void generarPedidoManual_devuelveStatusOk() {
//        doNothing().when(pedidoService).createPedido(new CrearPedidoDTO());
//
//        assertEquals(HttpStatus.OK, pedidoController.generarPedidoManual(new CrearPedidoDTO()).getStatusCode());
//    }
//
//    @Test
//    void verPedidosAceptados_devuelveStatusOkYlosPedidos() {
//        List<PedidoResponseDTO> pedidosAceptados = List.of(PedidoResponseDTO.builder().idPedido(1).estadoPedido("ACEPTADO").build());
//
//        when(pedidoService.obtenerPedidosAceptados()).thenReturn(pedidosAceptados);
//
//        assertEquals(HttpStatus.OK, pedidoController.verPedidosAceptados().getStatusCode());
//        assertEquals(pedidosAceptados, pedidoController.verPedidosAceptados().getBody());
//    }
//
//    @Test
//    void verPedidosRechazadosYpendientes_devuelveStatusOkYlosPedidos() {
//        List<PedidoResponseDTO> pedidosRechazadosYpendientes = List.of(PedidoResponseDTO.builder().idPedido(1).estadoPedido("RECHAZADO").build()
//        ,PedidoResponseDTO.builder().idPedido(2).estadoPedido("PENDIENTE").build());
//
//        when(pedidoService.obtenerPedidosRechazadosYpendientes()).thenReturn(pedidosRechazadosYpendientes);
//
//        assertEquals(HttpStatus.OK, pedidoController.verPedidosRechazadosYpendientes().getStatusCode());
//        assertEquals(pedidosRechazadosYpendientes, pedidoController.verPedidosRechazadosYpendientes().getBody());
//    }
//
//    @Test
//    void verAll_devuelveStatusOkYlosPedidos() {
//        List<PedidoResponseDTO> pedidos = List.of(PedidoResponseDTO.builder().idPedido(1).estadoPedido("RECHAZADO").build()
//                ,PedidoResponseDTO.builder().idPedido(2).estadoPedido("PENDIENTE").build(),
//                PedidoResponseDTO.builder().idPedido(3).estadoPedido("ACEPTADO").build());
//
//        when(pedidoService.obtenerPedidos()).thenReturn(pedidos);
//
//        assertEquals(HttpStatus.OK, pedidoController.verPedidos().getStatusCode());
//        assertEquals(pedidos, pedidoController.verPedidos().getBody());
//    }
//
//    @Test
//    void confirmarPedidodRecibido_devuelveStatusOK(){
//        doNothing().when(pedidoService).confirmarPedidoRecibido(1);
//        assertEquals(HttpStatus.OK,pedidoController.confirarPedidoRecibido(1).getStatusCode());
//    }
//}
