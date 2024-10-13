package com.gifa_api.controller;

import com.gifa_api.dto.metabase.MetabaseTokenResponseDTO;
import com.gifa_api.service.IMetabaseTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/metabase")
@RequiredArgsConstructor
public class MetabaseController {
    private final IMetabaseTokenService metabaseTokenService;

    @GetMapping("/token")
    public ResponseEntity<MetabaseTokenResponseDTO> getToken(@RequestParam Long dashboardId) {
        return ResponseEntity.ok(metabaseTokenService.generateTokenForDashboard(dashboardId));
    }
}
