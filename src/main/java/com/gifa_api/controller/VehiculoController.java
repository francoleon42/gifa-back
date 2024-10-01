package com.gifa_api.controller;

import com.gifa_api.dto.RegistarVehiculoDTO;
import com.gifa_api.model.Vehiculo;
import com.gifa_api.service.IVehiculoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vehiculo")
@RequiredArgsConstructor
public class VehiculoController {
    private final IVehiculoService vehiculoService;

    @GetMapping("/verAll")
    public List<Vehiculo> verVehiculos() {
        return vehiculoService.getVehiculos();
    }
    @PostMapping("/registrar")
    public String registrar(@RequestBody RegistarVehiculoDTO registarVehiculoDTO) throws Exception {
       vehiculoService.registrar(registarVehiculoDTO);
       return "Vehiculo registrado con exito";
    }

    @PatchMapping ("/inhabilitar/{id}")
    public String inhabilitar(@PathVariable Integer id) throws Exception {
        vehiculoService.inhabilitar(id);
        return "Vehiculo inhabilitado con exito";
    }
    @PatchMapping ("/habilitar/{id}")
    public String habilitar(@PathVariable Integer id) throws Exception {
        vehiculoService.inhabilitar(id);
        return "Vehiculo inhabilitado con exito";
    }




}
