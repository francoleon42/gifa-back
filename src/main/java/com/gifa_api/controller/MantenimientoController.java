package com.gifa_api.controller;

import com.gifa_api.dto.mantenimiento.*;
import com.gifa_api.model.Usuario;
import com.gifa_api.service.IMantenimientoService;
import com.gifa_api.service.IitemUsadoMantenimientoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mantenimiento")
@RequiredArgsConstructor
public class MantenimientoController {
    private final IMantenimientoService mantenimientoService;
    private final IitemUsadoMantenimientoService itemUsadoMantenimientoService;

    @PostMapping("/crearManual")
    public ResponseEntity<?> cargarMantenimientoManualmente(@RequestBody RegistrarMantenimientoDTO registrarMantenimientoDTO) {
        mantenimientoService.crearMantenimiento(registrarMantenimientoDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/porVehiculo/{id}")
    public ResponseEntity<MantenimientosResponseDTO> verMantenimientoPorVehiculo(@PathVariable Integer id) {
        return new ResponseEntity<>(mantenimientoService.verMantenimientosPorVehiculo(id), HttpStatus.OK);
    }

    @GetMapping("/verAll")
    public ResponseEntity<MantenimientosResponseDTO> verMantenimientos() {
        return new ResponseEntity<>(mantenimientoService.verMantenimientos(), HttpStatus.OK);
    }

    @GetMapping("/pendientes")
    public ResponseEntity<MantenimientosPendientesResponseDTO> verMantenimientosPendientes() {
        return new ResponseEntity<>(mantenimientoService.verMantenimientosPendientes(), HttpStatus.OK);
    }

    @PostMapping("/asignar/{mantenimientoId}")
    public ResponseEntity<?> asignarMantenimiento(@PathVariable Integer mantenimientoId,
                                                  @RequestBody AsignarMantenimientoRequestDTO asignarMantenimientoRequestDTO) {
        mantenimientoService.asignarMantenimiento(mantenimientoId, asignarMantenimientoRequestDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/finalizar/{mantenimientoId}")
    public ResponseEntity<?> finalizarMantenimiento(@PathVariable Integer mantenimientoId, @RequestBody FinalizarMantenimientoDTO finalizarMantenimientoDTO) {
        mantenimientoService.finalizarMantenimiento(mantenimientoId);
        itemUsadoMantenimientoService.agregaritemUtilizadoEnMantenimiento(mantenimientoId, finalizarMantenimientoDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/verMisMantenimientos")
    public ResponseEntity<MantenimientosResponseDTO> verMantenimientosDeOperador() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Usuario operador = (Usuario) authentication.getPrincipal();
        return new ResponseEntity<>(mantenimientoService.obtenerMantenimientosPorOperador(operador.getId()), HttpStatus.OK);

    }

}







