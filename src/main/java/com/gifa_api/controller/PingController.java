package com.gifa_api.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.gifa_api.model.Mantenimiento;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.gifa_api.service.IPingService;

import java.time.LocalDate;

@RestController
@RequestMapping("/ping")
@RequiredArgsConstructor
public class PingController {
    private final IPingService pingService;

    @GetMapping
    private ResponseEntity<String> ping(){
        return ResponseEntity.ok(pingService.getPong());
    }

}
