package com.gifa_api.controller;

import com.gifa_api.dto.login.LoginRequestDTO;
import com.gifa_api.dto.login.RegisterRequestDTO;
import com.gifa_api.service.IAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final IAuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginRequestDto){
        return ResponseEntity.ok(authService.login(loginRequestDto));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequestDTO registerRequestDto){
        return new ResponseEntity<>(authService.register(registerRequestDto), HttpStatus.CREATED);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String token) {
        authService.logout(token);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
