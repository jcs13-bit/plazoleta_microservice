package com.pragma.plazoleta_microservice.configuration.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;


@Service
public class JwtService {

    @Value("${secret}")
    private String SECRET_KEY;

    private Claims getAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(getKey())
                .build().
                parseClaimsJws(token)
                .getBody();
    }
    private Key getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    public String extractRoleFromToken(String token) {
        Claims claims = getAllClaims(token);
        return (String) claims.get("role");
    }
    public Long extractIdFromToken(String token) {
        Claims claims = getAllClaims(token);
        return claims.get("id", Long.class);
    }

}
