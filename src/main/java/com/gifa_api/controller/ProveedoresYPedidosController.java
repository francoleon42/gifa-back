package com.gifa_api.controller;

import com.gifa_api.dto.mantenimiento.RegistrarMantenimientoDTO;
import com.gifa_api.dto.proveedoresYPedidos.RegistroProveedorRequestDTO;
import com.gifa_api.service.IPedidoService;
import com.gifa_api.service.IProvedorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pedidos")
@RequiredArgsConstructor
public class ProveedoresYPedidosController {
    private final IPedidoService pedidoService;
    private final IProvedorService provedorService;


    @PostMapping("/registro")
    public ResponseEntity<?> registrarPedido(@RequestBody RegistroProveedorRequestDTO registroProveedorRequestDTO) {
        provedorService.registrarProveedor(registroProveedorRequestDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);

    }


}
