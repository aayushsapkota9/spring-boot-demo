package com.example.demo.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JwtUtils {

    // A secure 256-bit key for local development signing
    private final SecretKey key = Keys.hmacShaKeyFor("YourSuperSecretKeyMustBeAtLeast32BytesLong!!".getBytes());
    private final int jwtExpirationMs = 86400000; // 24 Hours

    // 1. Generate a token when a user logs in successfully
    public String generateJwtToken(Authentication authentication) {
        String username = authentication.getName();
        String roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        return Jwts.builder()
                .subject(username)
                .claim("roles", roles) // Put roles inside the token payload
                .issuedAt(new Date())
                .expiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(key)
                .compact();
    }

    // 2. Extract username from token
    public String getUserNameFromJwtToken(String token) {
        return getClaims(token).getSubject();
    }

    // 3. Extract roles from token
    public String getRolesFromJwtToken(String token) {
        return getClaims(token).get("roles", String.class);
    }

    // 4. Validate if token is altered or expired
    public boolean validateJwtToken(String authToken) {
        try {
            getClaims(authToken);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private Claims getClaims(String token) {
        return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
    }
}