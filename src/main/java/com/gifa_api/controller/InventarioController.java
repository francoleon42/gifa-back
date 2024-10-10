package com.gifa_api.controller;

import com.gifa_api.dto.item.ItemDeInventarioDTO;
import com.gifa_api.service.IItemDeIventarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventario")
@RequiredArgsConstructor
public class InventarioController {
    private final IItemDeIventarioService itemDeIventarioService;

    @PostMapping("/registrarItem")
    public ResponseEntity<?> registrarItem(@RequestBody ItemDeInventarioDTO itemDeInventarioDTO){
        itemDeIventarioService.registrar(itemDeInventarioDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PatchMapping("/utilizarItem/{id}")
    public ResponseEntity<?> utilizarItem(@PathVariable Integer id){
        itemDeIventarioService.utilizarItem(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/obtenerItems")
    public ResponseEntity<List<ItemDeInventarioDTO>> obtenerAllItems(){
        return ResponseEntity.ok(itemDeIventarioService.obtenerAllitems());
    }

}
