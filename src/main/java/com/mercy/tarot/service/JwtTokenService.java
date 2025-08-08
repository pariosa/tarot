package com.mercy.tarot.service;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import com.mercy.tarot.models.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtTokenService {

    private static final Logger log = LoggerFactory.getLogger(JwtTokenService.class);
    private static final String ROLES_CLAIM = "roles";
    private static final String USER_ID_CLAIM = "userId";
    private static final long EXPIRATION_TIME_MS = 86400000; // 24 hours

    private final SecretKey secretKey;
    private final FirebaseAuthService firebaseAuthService;

    public JwtTokenService(
            @Value("${jwt.secret}") String secret,
            FirebaseAuthService firebaseAuthService) {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes());
        this.firebaseAuthService = firebaseAuthService;
    }

    /**
     * Generates a JWT token for the given Firebase ID token
     */
    public String generateToken(String firebaseIdToken) {
        User user = firebaseAuthService.verifyTokenAndGetUser(firebaseIdToken);
        return generateToken(user);
    }

    /**
     * Generates a JWT token for the given User
     */
    public String generateToken(User user) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + EXPIRATION_TIME_MS);

        return Jwts.builder()
                .setSubject(user.getEmail())
                .claim(USER_ID_CLAIM, user.getId())
                .claim(ROLES_CLAIM, user.getRoles())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .compact();
    }

    /**
     * Validates a JWT token and returns the username
     */
    public String validateToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            return claims.getSubject();
        } catch (ExpiredJwtException ex) {
            log.error("JWT token expired: {}", ex.getMessage());
            throw new RuntimeException("Token expired");
        } catch (JwtException | IllegalArgumentException ex) {
            log.error("Invalid JWT token: {}", ex.getMessage());
            throw new RuntimeException("Invalid token");
        }
    }

    /**
     * Extracts user ID from JWT token
     */
    public Long getUserIdFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.get(USER_ID_CLAIM, Long.class);
    }

    /**
     * Extracts authorities/roles from JWT token
     */
    public Collection<? extends GrantedAuthority> getAuthorities(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();

        @SuppressWarnings("unchecked")
        List<String> roles = claims.get(ROLES_CLAIM, List.class);

        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toList());
    }

    /**
     * Refreshes a JWT token (extends expiration)
     */
    public String refreshToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + EXPIRATION_TIME_MS);

        return Jwts.builder()
                .setSubject(claims.getSubject())
                .claim(USER_ID_CLAIM, claims.get(USER_ID_CLAIM))
                .claim(ROLES_CLAIM, claims.get(ROLES_CLAIM))
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .compact();
    }
}