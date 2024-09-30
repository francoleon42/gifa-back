package com.gifa_api.controller;

import com.gifa_api.dto.VehiculoDTO;
import com.gifa_api.service.IVehiculoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/vehiculo")
@RequiredArgsConstructor
public class VehiculoController {
    IVehiculoService   vehiculoService;

    @PostMapping("/registrar")
    public String registrar(@RequestBody VehiculoDTO vehiculoDTO) throws Exception {
       vehiculoService.registrarVehiculo(vehiculoDTO);
       return "Vehiculo registrado con exito";
    }
}
