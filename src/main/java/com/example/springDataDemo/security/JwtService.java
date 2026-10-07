package com.example.springDataDemo.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.Date;
import java.util.HexFormat;

@Service
public class JwtService {
    @Value("${jwt.secret}")
    private String jwtSecret;
    private SecretKey getKey(){
        return Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    public String generateJwtAccessToken(UserDetails userDetails){
        return Jwts.builder().subject(userDetails.getUsername()).issuedAt(new Date()).expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 15))
                .claim("tokenType", "ACCESS")
                .signWith(getKey())
                .compact();
    }

    public String generateJwtRefreshToken(UserDetails userDetails, Instant expiresAt) {
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .issuedAt(new Date())
                .expiration(Date.from(expiresAt))
                .claim("tokenType", "REFRESH")
                .signWith(getKey())
                .compact();
    }

    public Claims parseToken(String token){
        return Jwts.parser().verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

    }

    public String hashToken(String token){
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            byte[] hash = digest.digest(
                    token.getBytes(StandardCharsets.UTF_8)
            );

            return HexFormat.of().formatHex(hash);

        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 not available", e);
        }
    }
}
