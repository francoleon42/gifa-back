package com.gifa_api.controller;

import com.gifa_api.dto.mantenimiento.*;
import com.gifa_api.service.IMantenimientoService;
import com.gifa_api.service.IitemUsadoMantenimientoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mantenimiento")
@RequiredArgsConstructor
public class MantenimientoController {
    private final IMantenimientoService mantenimientoService;
    private final IitemUsadoMantenimientoService itemUsadoMantenimientoService;

    @PostMapping("/crear-manualmente")
    public ResponseEntity<?> cargarMantenimientoManualmente(@RequestBody RegistrarMantenimientoDTO registrarMantenimientoDTO){
        mantenimientoService.crearMantenimiento(registrarMantenimientoDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @GetMapping("por-vehiculo/{id}")
    public MantenimientosResponseDTO verMantenimientoPorVehiculo(@PathVariable Integer id){
        return mantenimientoService.verMantenimientosPorVehiculo(id);
    }

    @GetMapping("/")
    public ResponseEntity<MantenimientosResponseDTO> verMantenimientos(){
        return new ResponseEntity<>(mantenimientoService.verMantenimientos(), HttpStatus.OK);
    }

    @GetMapping("/pendientes")
    public ResponseEntity<MantenimientosPendientesResponseDTO> verMantenimientosPendientes(){
        return new ResponseEntity<>(mantenimientoService.verMantenimientosPendientes(), HttpStatus.OK);
    }

    @PatchMapping("/asignar/{mantenimientoId}")
    public ResponseEntity<?> asignarMantenimiento(@PathVariable Integer mantenimientoId,
                                                  @RequestBody AsignarMantenimientoRequestDTO asignarMantenimientoRequestDTO){
        mantenimientoService.asignarMantenimiento(mantenimientoId, asignarMantenimientoRequestDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/finalizar/{mantenimientoId}")
    public ResponseEntity<?> finalizarMantenimiento(@PathVariable Integer mantenimientoId, FinalizarMantenimientoDTO finalizarMantenimientoDTO){
        mantenimientoService.finalizarMantenimiento(mantenimientoId,finalizarMantenimientoDTO);
        itemUsadoMantenimientoService.agregaritemUtilizadoEnMantenimiento(finalizarMantenimientoDTO.getIdItem(), mantenimientoId,finalizarMantenimientoDTO.getCantidad());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
