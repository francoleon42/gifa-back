package com.gifa_api.controller;

import com.gifa_api.dto.traccar.CrearDispositivoRequestDTO;
import com.gifa_api.service.IDispositivoService;
import com.gifa_api.service.ITraccarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/traccar")
public class TraccarController {
    private final ITraccarService traccarService;
    private  final IDispositivoService dispositivoService;

    @PostMapping("/crearDispositivo")
    public ResponseEntity<?> crearDispositivo(@RequestBody CrearDispositivoRequestDTO crearDispositivoRequestDTO,@RequestParam Integer idVehiculo){
        traccarService.crearDispositivo(crearDispositivoRequestDTO);
        dispositivoService.crearDispositivo(crearDispositivoRequestDTO,idVehiculo);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/getDispositivos")
    public ResponseEntity<?> getDispositivos(){
        return new ResponseEntity<>(traccarService.obtenerDispositivos(),HttpStatus.OK);
    }
}
