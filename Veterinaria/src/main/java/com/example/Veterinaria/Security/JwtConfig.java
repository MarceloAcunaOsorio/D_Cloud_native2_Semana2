package com.example.Veterinaria.Security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtConfig {
    @Value("${app.jwt-secret}")
    private String secret;

    @Value("${app.jwt-expiration-milliseconds}")
    private int expiration;

    public String getSecret() {
        return secret;
    }

    public int getExpiration() {
        return expiration;
    }
} 