package com.gifa_api.controller;

import com.gifa_api.dto.vehiculo.ListaVehiculosResponseDTO;
import com.gifa_api.dto.vehiculo.RegistarVehiculoDTO;
import com.gifa_api.service.IVehiculoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vehiculo")
@RequiredArgsConstructor
public class VehiculoController {
    private final IVehiculoService vehiculoService;

    @GetMapping("/verAll")
    public ResponseEntity<ListaVehiculosResponseDTO> verVehiculos() {
        return new ResponseEntity<>(vehiculoService.getVehiculos(), HttpStatus.OK);
    }
    @PostMapping("/registrar")
    public ResponseEntity<?> registrar(@RequestBody RegistarVehiculoDTO registarVehiculoDTO) {
       vehiculoService.registrar(registarVehiculoDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PatchMapping ("/inhabilitar/{id}")
    public ResponseEntity<?> inhabilitar(@PathVariable Integer id) {
        vehiculoService.inhabilitar(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping ("/habilitar/{id}")
    public ResponseEntity<?> habilitar(@PathVariable Integer id) {
        vehiculoService.habilitar(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/historialVehiculo/{vehiculoId}")
    public ResponseEntity<?> historialVehiculo(@PathVariable Integer vehiculoId) {
        return new ResponseEntity<>(vehiculoService.obtenerHistorialDeVehiculo(vehiculoId), HttpStatus.OK);
    }


}
