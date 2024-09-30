package com.gifa_api.controller;

import com.gifa_api.model.Mantenimiento;
import com.gifa_api.service.IMantenimientoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/mantenimiento")
@RequiredArgsConstructor
public class MantenimientoController {
    IMantenimientoService mantenimientoService;

    @GetMapping("ver_mantenimientos/{id}")
    public List<Mantenimiento> verMantenimientos(@PathVariable Integer id){
        return mantenimientoService.verMantenimientos(id);
    }
}
