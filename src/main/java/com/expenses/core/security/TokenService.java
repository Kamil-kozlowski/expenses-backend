package com.expenses.core.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClaims;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class TokenService {

    private static final String EXPIRATION_DATE_KEY = "expirationDate";
    private static final String TOKEN_PREFIX = "Bearer ";

    private final TokenProperties tokenProperties;

    public String generateToken(String username) {
        final Claims claims = new DefaultClaims()
                .setIssuedAt(new Date())
                .setSubject(username);
        claims.put(EXPIRATION_DATE_KEY, generateExpirationTimestamp());
        return this.generateToken(claims);
    }

    public String refreshToken(@NonNull final String token) {
        final Claims claims = this.getClaimsFromToken(token);
        claims.put(EXPIRATION_DATE_KEY, generateExpirationTimestamp());
        return this.generateToken(claims);
    }

    public boolean isToken(String header) {
        return header != null && header.startsWith(TOKEN_PREFIX);
    }

    public String extractToken(@NonNull final String header) {
        return header.substring(TOKEN_PREFIX.length());
    }

    public Boolean isValidToken(@NonNull final String token) {
        try {
            return !isTokenExpired(token);
        } catch (Exception e) {
            return false;
        }
    }

    public String getUsernameFromToken(@NonNull final String token) {
        return getClaimsFromToken(token).getSubject();
    }

    public String prepareTokenHeader(@NonNull final String token) {
        return TOKEN_PREFIX + token;
    }

    private boolean isTokenExpired(String token) {
        final long now = System.currentTimeMillis();
        final long expiration = this.getExpirationTimestamp(token);
        return expiration < now;
    }

    private long getExpirationTimestamp(String token) {
        return (long) getClaimsFromToken(token).get(EXPIRATION_DATE_KEY);
    }

    private Claims getClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(tokenProperties.getSecret().getBytes())
                .parseClaimsJws(token)
                .getBody();
    }

    private String generateToken(Claims claims) {
        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, tokenProperties.getSecret().getBytes())
                .compact();
    }

    private long generateExpirationTimestamp() {
        return System.currentTimeMillis() + tokenProperties.getExpirationTimeMs();
    }
}
