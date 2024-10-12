package com.gifa_api.controller;

import com.gifa_api.dto.mantenimiento.*;
import com.gifa_api.exception.BadRoleException;
import com.gifa_api.exception.NotFoundException;
import com.gifa_api.model.Usuario;
import com.gifa_api.service.IMantenimientoService;
import com.gifa_api.service.IitemUsadoMantenimientoService;
import com.gifa_api.utils.enums.Rol;
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


    @GetMapping("/finalizados")
    public ResponseEntity<MantenimientosResponseDTO> verMantenimientosFinalizados() {
        return new ResponseEntity<>(mantenimientoService.verMantenimientosFinalizados(), HttpStatus.OK);
    }

    @GetMapping("/pendientes")
    public ResponseEntity<MantenimientosResponseDTO> verMantenimientosPendientes() {
        return new ResponseEntity<>(mantenimientoService.verMantenimientosPendientes(), HttpStatus.OK);
    }

    @PostMapping("/asignar/{mantenimientoId}")
    public ResponseEntity<?> asignarMantenimiento(@PathVariable Integer mantenimientoId) {
        Usuario operador = getUserFromToken();
        mantenimientoService.asignarMantenimiento(mantenimientoId, operador);
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
        Usuario operador = getUserFromToken();
        return new ResponseEntity<>(mantenimientoService.obtenerMantenimientosPorOperador(operador.getId()), HttpStatus.OK);

    }

    private Usuario getUserFromToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication != null && authentication.isAuthenticated())) {
            throw new NotFoundException("El token no corresponde a un usuario.");
        }

        Usuario usuario = (Usuario) authentication.getPrincipal();
        if (usuario.getRol() != Rol.OPERADOR) {
            throw new BadRoleException("El usuario no corresponde a un operador.");
        }

        return usuario;
    }
}







