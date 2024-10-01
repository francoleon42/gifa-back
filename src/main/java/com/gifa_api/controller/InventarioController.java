package com.gifa_api.controller;

import com.gifa_api.dto.RegistrarItemDeInventarioDTO;
import com.gifa_api.service.IItemDeIventarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inventario")
@RequiredArgsConstructor
public class InventarioController {
    private final IItemDeIventarioService itemDeIventarioService;

    @PostMapping("/registrarItem")
    public String registrarItem(@RequestBody RegistrarItemDeInventarioDTO registrarItemDeInventarioDTO){
        itemDeIventarioService.registrar(registrarItemDeInventarioDTO);
        return "Registro con exito del item";
    }

    @PatchMapping("/utilizarItem/{id}")
    public String utilizarItem(@PathVariable Integer id){
        itemDeIventarioService.utilizarItem(id);
        return "se disminuyo el stock con exito";
    }

}
