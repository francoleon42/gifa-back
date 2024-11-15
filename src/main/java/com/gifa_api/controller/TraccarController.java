package com.gifa_api.controller;

import com.gifa_api.service.IDispositivoService;
import com.gifa_api.service.ITraccarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

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

    @GetMapping("/verInconsistenciasDeCombustible")
    public ResponseEntity<?> verInconsistenciasDeCombustible(@RequestParam LocalDate from,  @RequestParam LocalDate to) {
        return new ResponseEntity<>(traccarService.getInconsistencias(from, to), HttpStatus.OK);
    }

    @GetMapping("/getPosicionesEnVivo/{unicoId}")
    public ResponseEntity<?> getPosicionesEnVivo(@PathVariable String unicoId){
        return new ResponseEntity<>(traccarService.obtenerPosicionesEnVivo(unicoId),HttpStatus.OK);
    }

    @GetMapping("/getPosicionesEnRangoDeFechas/{unicoId}")
    public ResponseEntity<?> obtenerPosicionesEnRangoDeFechas(@PathVariable String unicoId, @RequestParam LocalDate from,  @RequestParam LocalDate to){
        return new ResponseEntity<>(traccarService.obtenerPosicionesEnRangoDeFechas(unicoId,from,to),HttpStatus.OK);
    }

}
