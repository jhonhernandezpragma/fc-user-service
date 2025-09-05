package com.pragma.fc.user_service.infraestructure.out.security.adapter;

import com.pragma.fc.user_service.domain.spi.ITokenServicePort;
import com.pragma.fc.user_service.infraestructure.out.security.entity.TokenType;
import com.pragma.fc.user_service.infraestructure.out.security.property.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.Map;
import java.util.function.Function;

public class JwtTokenServiceAdapter implements ITokenServicePort {
    private final JwtProperties jwtProperties;

    public JwtTokenServiceAdapter(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    @Override
    public String generateAccessToken(String subject, Map<String, Object> claims) {
        claims.put("type", TokenType.ACCESS.name());
        return buildToken(subject, claims, jwtProperties.getExpiration());
    }

    @Override
    public String generateRefreshToken(String subject) {
        return buildToken(subject, Map.of("type", TokenType.REFRESH.name()), jwtProperties.getRefreshExpiration());
    }

    @Override
    public boolean isAccessTokenValid(String token) {
        if (!isTokenValidGeneric(token)) return false;
        String tokenType = extractClaim(token, claims -> claims.get("type", String.class));
        return TokenType.ACCESS.name().equals(tokenType);
    }

    @Override
    public boolean isRefreshTokenValid(String token) {
        if (!isTokenValidGeneric(token)) return false;
        String tokenType = extractClaim(token, claims -> claims.get("type", String.class));
        return TokenType.REFRESH.name().equals(tokenType);
    }

    @Override
    public String extractSubject(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    @Override
    public String extractRole(String token) {
        return extractClaim(token, claims -> claims.get("role", String.class));
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimExtractor) {
        Claims claims = extractAllClaims(token);
        return claimExtractor.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(jwtProperties.getSecret())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private boolean isTokenValidGeneric(String token) {
        Date expiration = extractClaim(token, Claims::getExpiration);
        Date notBefore = extractClaim(token, Claims::getNotBefore);
        return (expiration.after(new Date()) && notBefore.before(new Date()));
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
