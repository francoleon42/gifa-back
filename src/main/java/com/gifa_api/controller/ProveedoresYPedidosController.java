package com.gifa_api.controller;

import com.gifa_api.dto.mantenimiento.RegistrarMantenimientoDTO;
import com.gifa_api.dto.proveedoresYPedidos.AsociacionProveedorDeITemDTO;
import com.gifa_api.dto.proveedoresYPedidos.RegistroProveedorRequestDTO;
import com.gifa_api.model.ProveedorDeItem;
import com.gifa_api.service.IPedidoService;
import com.gifa_api.service.IProvedorService;
import com.gifa_api.service.IProveedorDeItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pedidos")
@RequiredArgsConstructor
public class ProveedoresYPedidosController {
    private final IPedidoService pedidoService;
    private final IProvedorService provedorService;
    private final IProveedorDeItemService proveedorDeItemService;


    @PostMapping("/registro")
    public ResponseEntity<?> registrarProveedor(@RequestBody RegistroProveedorRequestDTO registroProveedorRequestDTO) {
        provedorService.registrarProveedor(registroProveedorRequestDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);

    }

    @PostMapping("/asociar-proveedor")
    public ResponseEntity<?> asociarProveedorAItem(AsociacionProveedorDeITemDTO asociacionProveedorDeITemDTO) {
        proveedorDeItemService.asociarProveedorAItem(asociacionProveedorDeITemDTO);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }



}
