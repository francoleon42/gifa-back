package com.gifa_api.controller;

import com.gifa_api.dto.chofer.ChoferRegistroDTO;
import com.gifa_api.service.IChoferService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chofer")
@RequiredArgsConstructor
public class ChoferController {
    private IChoferService choferService;

    @PostMapping("/registrar")
    public ResponseEntity<?> createChofer(@RequestBody ChoferRegistroDTO choferRegistroDTO) {
        choferService.registro(choferRegistroDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
