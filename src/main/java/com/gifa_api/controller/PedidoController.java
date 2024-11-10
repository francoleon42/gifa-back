package com.gifa_api.controller;

import com.gifa_api.dto.pedido.*;
import com.gifa_api.service.IPedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedido")
@RequiredArgsConstructor
public class PedidoController {
    private final IPedidoService pedidoService;

    // Pedidos
    @PostMapping("/generarPedido")
    public ResponseEntity<?> generarPedidoManual(@RequestBody CrearPedidoDTO pedidoManualDTO){
        pedidoService.crearPedidoManual(pedidoManualDTO);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }
    @GetMapping("/aceptados")
    public ResponseEntity<List<PedidoResponseDTO>> verPedidosAceptados() {
        return new ResponseEntity<>(pedidoService.obtenerPedidosAceptados(),HttpStatus.OK);
    }
    @GetMapping("/getRechazadosPendientesPresupuestoInsuficiente")
    public ResponseEntity<List<PedidoResponseDTO>> verPedidosRechazadosYpendientesYpresupuestoInsuficiente() {
        return new ResponseEntity<>(pedidoService.obtenerPedidosRechazadosYpendientesYpresupuestoInsuficiente(),HttpStatus.OK);
    }
    @GetMapping("/verAll")
    public ResponseEntity<List<PedidoResponseDTO>> verPedidos(){
        return ResponseEntity.ok(pedidoService.obtenerPedidos());
    }

    @PatchMapping("/confirmarPedidoRecibido/{id}")
    public ResponseEntity<?> confirarPedidoRecibido(@PathVariable Integer id) {
        pedidoService.confirmarPedidoRecibido(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }








}
