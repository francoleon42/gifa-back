package com.gifa_api.controller;

import com.gifa_api.dto.proveedoresYPedidos.*;
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


    @PostMapping("/registrarProveedor")
    public ResponseEntity<?> registrarProveedor(@RequestBody RegistroProveedorRequestDTO registroProveedorRequestDTO) {
        provedorService.registrarProveedor(registroProveedorRequestDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/asociarProveedor")
    public ResponseEntity<?> asociarProveedorAItem(@RequestBody ProveedorDeITemRequestDTO proveedorDeITemRequestDTO) {
        proveedorDeItemService.asociarProveedorAItem(proveedorDeITemRequestDTO);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }
    @GetMapping("/allProveedores")
    public ResponseEntity<?> verProveedores(){
        return new ResponseEntity<>(provedorService.obtenerProveedores(), HttpStatus.OK);
    }

    @GetMapping("/verProveedoresDeItems")
    public ResponseEntity<?> verAsociacionesDeProveedoresConItems(){
        return new ResponseEntity<>(proveedorDeItemService.obtenerAll(), HttpStatus.OK);
    }


    // Pedidos
    @PostMapping("/generarPedido")
    public ResponseEntity<?> generarPedidoManual(@RequestBody CrearPedidoDTO pedidoManualDTO){
        pedidoService.createPedido(pedidoManualDTO);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }
    @GetMapping("/aceptados")
    public ResponseEntity<List<PedidoResponseDTO>> verPedidosAceptados() {
        return new ResponseEntity<>(pedidoService.obtenerPedidosAceptados(),HttpStatus.OK);
    }
    @GetMapping("/rechazadosYpendientes")
    public ResponseEntity<List<PedidoResponseDTO>> verPedidosRechazadosYpendientes() {
        return new ResponseEntity<>(pedidoService.obtenerPedidosRechazadosYpendientes(),HttpStatus.OK);
    }
    @GetMapping("/verAll")
    public ResponseEntity<List<PedidoResponseDTO>> verPedidos(){
        return ResponseEntity.ok(pedidoService.obtenerPedidos());
    }








}
