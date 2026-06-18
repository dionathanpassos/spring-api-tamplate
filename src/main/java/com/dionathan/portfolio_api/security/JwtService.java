package com.dionathan.portfolio_api.security;

import com.dionathan.portfolio_api.auth.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtService {


    private final SecretKey key;

    public JwtService(@Value("${jwt.secret}") String secret) {
        this.key = Keys.hmacShaKeyFor(
                secret.getBytes(StandardCharsets.UTF_8)
        );
    }

    public String generateAccessToken(User user) {
        return Jwts
                .builder()
                .subject(user.getId().toString())
                .claim("role", user.getRole().name())
                .claim("type", "access")
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 15 * 60 * 1000))
                .signWith(key)
                .compact();
    }

    public String generateRefreshToken(User user) {
        return Jwts
                .builder()
                .subject(user.getId().toString())
                .claim("type", "refresh")
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 7L * 24 * 60 * 60 * 1000))
                .signWith(key)
                .compact();
    }

    public String generateValidationToken(User user) {
        return Jwts
                .builder()
                .subject(user.getId().toString())
                .claim("type", "validation")
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 24L * 60 * 60 * 1000))
                .signWith(key)
                .compact();
    }

    public String generateResetPasswordToken(User user) {
        return Jwts
                .builder()
                .subject(user.getId().toString())
                .claim("type", "reset-password")
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 30L * 60L * 1000L))
                .signWith(key)
                .compact();
    }

    public Long validateAccessToken(String token) {
        Claims claims = extractClaims(token);
        String type = claims.get("type", String.class);

        if(!"access".equals(type)) {
            throw new JwtException("Expected access token");
        }
        return Long.parseLong(claims.getSubject());
    }

    public Long validateRefreshToken(String token) {
        Claims claims = extractClaims(token);
        String type = claims.get("type", String.class);

        if(!"refresh".equals(type)) {
            throw new JwtException("Expected refresh token");
        }

        return Long.parseLong(claims.getSubject());
    }

    public Long validateValidationToken(String token) {
        Claims claims = extractClaims(token);
        String type = claims.get("type", String.class);

        if(!"validation".equals(type)) {
            throw new JwtException("Expected validation token");
        }
        return Long.parseLong(claims.getSubject());
    }

    public Long validateResetPasswordToken(String token) {
        Claims claims = extractClaims(token);
        String type = claims.get("type", String.class);

        if(!"reset-password".equals(type)) {
            throw new JwtException("Expected reset password token");
        }
        return Long.parseLong(claims.getSubject());
    }


    private Claims extractClaims(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
