package com.gifa_api.controller;

import com.gifa_api.dto.traccar.CrearDispositivoRequestDTO;
import com.gifa_api.dto.traccar.VerInconsistenciasRequestDTO;
import com.gifa_api.service.IDispositivoService;
import com.gifa_api.service.IPosicionService;
import com.gifa_api.service.ITraccarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/traccar")
public class TraccarController {
    private final ITraccarService traccarService;
    private  final IDispositivoService dispositivoService;
    private  final IPosicionService posicionService;


    @GetMapping("/getDispositivos")
    public ResponseEntity<?> getDispositivos(){
        return new ResponseEntity<>(traccarService.obtenerDispositivos(),HttpStatus.OK);
    }

    @GetMapping("/verInconsistenciasDeCombustible/{fecha}")
    public ResponseEntity<?> verInconsistenciasDeCombustible(@PathVariable LocalDate fecha){
        return new ResponseEntity<>(traccarService.getInconsistencias(fecha),HttpStatus.OK);
    }

    @GetMapping("/getPosiciones/{unicoId}")
    public ResponseEntity<?> getPosiciones(@PathVariable String unicoId){
        return new ResponseEntity<>(posicionService.getPosicionesDeDispositivo(unicoId),HttpStatus.OK);
    }

}
