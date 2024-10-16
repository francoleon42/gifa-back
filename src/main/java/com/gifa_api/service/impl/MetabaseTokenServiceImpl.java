package com.gifa_api.service.impl;

import com.gifa_api.dto.metabase.MetabaseTokenResponseDTO;
import com.gifa_api.service.IMetabaseTokenService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class MetabaseTokenServiceImpl implements IMetabaseTokenService {
    private static final String SECRET_KEY = System.getenv("METABASE_SECRET_KEY");
    private static final long EXPIRATION_TIME = 600_000;

    @Override
    public MetabaseTokenResponseDTO generateTokenForDashboard(Long dashboardId) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("resource", Map.of("dashboard", dashboardId));
        payload.put("params", new HashMap<String, Object>());
        payload.put("exp", (new Date().getTime() / 1000) + (EXPIRATION_TIME / 1000));

        SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

        String token;
        try {
            token = Jwts.builder()
                    .setClaims(payload)
                    .signWith(key, SignatureAlgorithm.HS256)
                    .compact();
        } catch (Exception e) {
            throw new RuntimeException("Error al generar el token: " + e.getMessage());
        }


        return MetabaseTokenResponseDTO.builder()
                .token(token)
                .build();
    }
}
