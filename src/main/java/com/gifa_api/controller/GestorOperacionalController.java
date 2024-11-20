package com.gifa_api.controller;


import com.gifa_api.dto.gestorOperacional.GestorOperacionalConsumoDeLitrosPorKmRequestDTO;
import com.gifa_api.dto.gestorOperacional.GestorOperacionalPresupuestoRequestDTO;
import com.gifa_api.dto.gestorOperacional.GestorOperacionalResponseDTO;
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
    @PatchMapping("/actualizarPresupuesto")
    public ResponseEntity<?> actualizarGestorDePedidos(@RequestBody GestorOperacionalPresupuestoRequestDTO gestorOperacionalPresupuestoRequestDTO){
        gestorOperacionalService.actualizarPresupuesto(gestorOperacionalPresupuestoRequestDTO);
        return ResponseEntity.ok(HttpStatus.OK);
    }
    @PatchMapping("/actualizarConsumoDeLitrosPorKm")
    public ResponseEntity<?> actualizarConsumoDeLitrosPorKm(@RequestBody GestorOperacionalConsumoDeLitrosPorKmRequestDTO gestorOperacionalConsumoDeLitrosPorKmRequestDTO){
        gestorOperacionalService.actualizarconsumoDeLitrosPorKm(gestorOperacionalConsumoDeLitrosPorKmRequestDTO);
        return ResponseEntity.ok(HttpStatus.OK);
    }
    @GetMapping("/obtener")
    public ResponseEntity<GestorOperacionalResponseDTO> obtenerGestorDePedidos() {
        return ResponseEntity.ok(gestorOperacionalService.obtenerGestorOperacional());
    }
}
