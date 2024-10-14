package com.gifa_api.controller;

import com.gifa_api.dto.traccar.CrearDispositivoRequestDTO;
import com.gifa_api.service.ITraccarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/traccar")
public class TraccaController {
    private final ITraccarService traccarService;

    @PostMapping("/crearDispositivo")
    public ResponseEntity<?> crearDispositivo(CrearDispositivoRequestDTO crearDispositivoRequestDTO){
//        System.out.println(crearDispositivoRequestDTO.getNombre());
//
        CrearDispositivoRequestDTO crearDispositivoRequestDTO1 = CrearDispositivoRequestDTO.builder().nombre("nombrehard").uniqueID("9999").build();
        traccarService.crearDispositivo(crearDispositivoRequestDTO1);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
