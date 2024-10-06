package com.gifa_api.controller;


import com.gifa_api.dto.gestionDeCombustilble.CargaCombustibleRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/gestionDeCombustible")
@RequiredArgsConstructor
public class GestionDeCombustibleController {
    private final

    @PostMapping("/cargarCombustible")
    public ResponseEntity<?> registrarCombustible(@RequestBody CargaCombustibleRequestDTO cargaCombustibleRequestDTO){

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
