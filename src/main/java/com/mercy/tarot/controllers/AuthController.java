package com.mercy.tarot.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mercy.tarot.dto.AuthRequest;
import com.mercy.tarot.dto.AuthResponse;
import com.mercy.tarot.models.User;
import com.mercy.tarot.service.FirebaseAuthService;
import com.mercy.tarot.service.JwtTokenService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final FirebaseAuthService firebaseAuthService;
    private final JwtTokenService jwtTokenService;

    public AuthController(FirebaseAuthService firebaseAuthService,
            JwtTokenService jwtTokenService) {
        this.firebaseAuthService = firebaseAuthService;
        this.jwtTokenService = jwtTokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest authRequest) {
        try {
            // Use your existing service to verify token and get user
            User user = firebaseAuthService.verifyTokenAndGetUser(authRequest.getIdToken());

            // Generate JWT token for your backend
            String jwtToken = jwtTokenService.generateToken(user);

            return ResponseEntity.ok(new AuthResponse(jwtToken, "Login successful"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new AuthResponse(null, "Authentication failed: " + e.getMessage()));
        }
    }

    @GetMapping("/validate")
    public ResponseEntity<Boolean> validateToken(@RequestHeader("Authorization") String authHeader) {
        try {
            String token = authHeader.substring(7); // Remove "Bearer " prefix
            jwtTokenService.validateToken(token);
            return ResponseEntity.ok(true);
        } catch (Exception e) {
            return ResponseEntity.ok(false);
        }
    }
}