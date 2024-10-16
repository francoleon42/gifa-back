package com.gifa_api.controller;

import com.gifa_api.dto.chofer.AsignarChoferDTO;
import com.gifa_api.dto.chofer.ChoferRegistroDTO;
import com.gifa_api.service.IChoferService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/chofer")
@RequiredArgsConstructor
public class ChoferController {
    private final IChoferService choferService;

    @PostMapping("/registrar")
    public ResponseEntity<?> createChofer(@RequestBody ChoferRegistroDTO choferRegistroDTO) {
        choferService.registro(choferRegistroDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @PatchMapping("/asignarChofer")
    public ResponseEntity<?> AsignarChofer(@RequestBody AsignarChoferDTO asignarChoferDTO) {
        choferService.asignarVehiculo(asignarChoferDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/habilitar/{id}")
    public ResponseEntity<?> habilitar(@PathVariable Integer idChofer) {
        choferService.habilitar(idChofer);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PatchMapping("/inhabilitar/{id}")
    public ResponseEntity<?> createChofer(@PathVariable Integer idChofer) {
        choferService.inhabilitar(idChofer);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/verChoferes")
    public ResponseEntity<?> getAllChofers() {
        return new ResponseEntity<>(choferService.obtenerAll(),HttpStatus.OK);
    }
}
