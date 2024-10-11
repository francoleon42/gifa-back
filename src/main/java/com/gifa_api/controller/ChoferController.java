package com.gifa_api.controller;

import com.gifa_api.dto.chofer.ChoferEditDTO;
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

    @PatchMapping("/habilitar")
    public ResponseEntity<?> habilitar(@RequestBody ChoferEditDTO choferEditDTO) {
        choferService.habilitar(choferEditDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PatchMapping("/inhabilitar")
    public ResponseEntity<?> createChofer(@RequestBody ChoferEditDTO choferEditDTO) {
        choferService.inhabilitar(choferEditDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

//    @GetMapping("/verChofers")
//    public ResponseEntity<?> getAllChofers() {
//        return
//    }
}
