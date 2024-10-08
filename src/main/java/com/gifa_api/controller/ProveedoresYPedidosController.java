package com.gifa_api.controller;

import com.gifa_api.dto.proveedoresYPedidos.*;
import com.gifa_api.service.IGestorDePedidosService;
import com.gifa_api.service.IPedidoService;
import com.gifa_api.service.IProvedorService;
import com.gifa_api.service.IProveedorDeItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedido")
@RequiredArgsConstructor
public class ProveedoresYPedidosController {
    private final IPedidoService pedidoService;
    private final IProvedorService provedorService;
    private final IProveedorDeItemService proveedorDeItemService;
    private final IGestorDePedidosService gestorDePedidosService;

    @PostMapping("/registrarProveedor")
    public ResponseEntity<?> registrarProveedor(@RequestBody RegistroProveedorRequestDTO registroProveedorRequestDTO) {
        provedorService.registrarProveedor(registroProveedorRequestDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/asociarProveedor")
    public ResponseEntity<?> asociarProveedorAItem(@RequestBody AsociacionProveedorDeITemDTO asociacionProveedorDeITemDTO) {
        proveedorDeItemService.asociarProveedorAItem(asociacionProveedorDeITemDTO);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @GetMapping("/obtenerGestorDePedido")
    public ResponseEntity<GestorDePedidosDTO> obtenerGestorDePedidos() {
        return ResponseEntity.ok(gestorDePedidosService.obtenerGestorDePedidos());
    }

    @PatchMapping("/actualizarGestor")
    public ResponseEntity<?> actualizarGestorDePedidos(@RequestBody GestorDePedidosDTO gestorDePedidosDTO){
        gestorDePedidosService.actualizarGestorDePedidos(gestorDePedidosDTO);
        return ResponseEntity.ok(HttpStatus.OK);
    }
    @GetMapping("/verAll")
    public ResponseEntity<List<PedidoResponseDTO>> verPedidos(){
        return ResponseEntity.ok(pedidoService.obtenerPedidos());
    }

    @PostMapping("/generarPedido")
    public ResponseEntity<?> generarPedido(@RequestBody PedidoManualDTO pedidoManualDTO){
        pedidoService.createPedido(pedidoManualDTO.getIdItem(), pedidoManualDTO.getCantidad(),pedidoManualDTO.getMotivo());
        return ResponseEntity.ok(HttpStatus.CREATED);
    }




}
