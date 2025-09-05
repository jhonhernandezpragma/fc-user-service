package com.pragma.fc.user_service.infraestructure.out.security.adapter;

import com.pragma.fc.user_service.domain.spi.ITokenServicePort;
import com.pragma.fc.user_service.infraestructure.out.security.property.JwtProperties;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtTokenServiceAdapter implements ITokenServicePort {
    private final JwtProperties jwtProperties;

    public JwtTokenServiceAdapter(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    @Override
    public String generateAccessToken(String subject, Map<String, Object> claims) {
        return buildToken(subject, claims, jwtProperties.getExpiration());
    }

    @Override
    public String generateRefreshToken(String subject) {
        return buildToken(subject, new HashMap<>(), jwtProperties.getRefreshExpiration());
    }

    private String buildToken(String subject, Map<String, Object> claims, Long duration) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setNotBefore(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + duration))
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecret())
                .compact();
    }
}
