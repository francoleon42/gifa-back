package com.gifa_api.controller;

import com.gifa_api.dto.mantenimiento.RegistrarMantenimientoDTO;
import com.gifa_api.dto.proveedoresYPedidos.AsociacionProveedorDeITemDTO;
import com.gifa_api.dto.proveedoresYPedidos.GestorDePedidosDTO;
import com.gifa_api.dto.proveedoresYPedidos.RegistroProveedorRequestDTO;
import com.gifa_api.model.GestorDePedidos;
import com.gifa_api.model.ProveedorDeItem;
import com.gifa_api.service.IGestorDePedidosService;
import com.gifa_api.service.IPedidoService;
import com.gifa_api.service.IProvedorService;
import com.gifa_api.service.IProveedorDeItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pedido")
@RequiredArgsConstructor
public class ProveedoresYPedidosController {
    private final IPedidoService pedidoService;
    private final IProvedorService provedorService;
    private final IProveedorDeItemService proveedorDeItemService;
    private final IGestorDePedidosService gestorDePedidosService;

    @PostMapping("/registrar-proveedor")
    public ResponseEntity<?> registrarProveedor(@RequestBody RegistroProveedorRequestDTO registroProveedorRequestDTO) {
        provedorService.registrarProveedor(registroProveedorRequestDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);

    }

    @PostMapping("/asociar-proveedor")
    public ResponseEntity<?> asociarProveedorAItem(@RequestBody AsociacionProveedorDeITemDTO asociacionProveedorDeITemDTO) {
        proveedorDeItemService.asociarProveedorAItem(asociacionProveedorDeITemDTO);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @GetMapping("obtener-gestorDePedido")
    public GestorDePedidosDTO obtenerGestorDePedidos() {
        return gestorDePedidosService.obtenerGestorDePedidos();
    }
    @PatchMapping("/actualizar-gestor")
    public ResponseEntity<?> actualizarGestorDePedidos(@RequestBody GestorDePedidosDTO gestorDePedidosDTO){
        gestorDePedidosService.actualizarGestorDePedidos(gestorDePedidosDTO);
        return ResponseEntity.ok(HttpStatus.OK);
    }
//    @GetMapping("/verPedidos")
//    public PedidosDTO verPedidos(){
//        return pedidoService.obtenerPedidos();
//    }




}
