package com.gifa_api.controller;

import com.gifa_api.dto.GestorOperacionalDTO;
import com.gifa_api.service.IGestorOperacionalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/gestorOperacional")
@RequiredArgsConstructor
public class GestorOperacionalController {

    private final IGestorOperacionalService gestorOperacionalService;
    @PatchMapping("/actualizar")
    public ResponseEntity<?> actualizarGestorDePedidos(@RequestBody GestorOperacionalDTO gestorDePedidosDTO){
        gestorOperacionalService.actualizarGestorOperacional(gestorDePedidosDTO);
        return ResponseEntity.ok(HttpStatus.OK);
    }
    @GetMapping("/obtener")
    public ResponseEntity<GestorOperacionalDTO> obtenerGestorDePedidos() {
        return ResponseEntity.ok(gestorOperacionalService.obtenerGestorOperacional());
    }
}
