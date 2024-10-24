package com.gifa_api.controller;

import com.gifa_api.dto.proveedoresYPedidos.ProveedorDeITemRequestDTO;
import com.gifa_api.dto.proveedoresYPedidos.RegistroProveedorRequestDTO;
import com.gifa_api.service.IProvedorService;
import com.gifa_api.service.IProveedorDeItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/proveedor")
@RequiredArgsConstructor
public class ProveedorController {
    private final IProvedorService provedorService;
    private final IProveedorDeItemService proveedorDeItemService;

    @PostMapping("/registrar")
    public ResponseEntity<?> registrar(@RequestBody RegistroProveedorRequestDTO registroProveedorRequestDTO) {
        provedorService.registrarProveedor(registroProveedorRequestDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/asociarAitem")
    public ResponseEntity<?> asociarProveedorAItem(@RequestBody ProveedorDeITemRequestDTO proveedorDeITemRequestDTO) {
        proveedorDeItemService.asociarProveedorAItem(proveedorDeITemRequestDTO);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }
    @GetMapping("/verAll")
    public ResponseEntity<?> allProveedores(){
        return new ResponseEntity<>(provedorService.obtenerProveedores(), HttpStatus.OK);
    }

    @GetMapping("/verProveedoresDeItems")
    public ResponseEntity<?> allProveedoresConItems(){
        return new ResponseEntity<>(proveedorDeItemService.obtenerAll(), HttpStatus.OK);
    }
}
