package com.mercy.tarot.controllers;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mercy.tarot.dto.LoginRequest;
import com.mercy.tarot.dto.LoginResponse;
import com.mercy.tarot.dto.UserRegistrationDTO;
import com.mercy.tarot.models.User;
import com.mercy.tarot.service.JwtTokenService;
import com.mercy.tarot.service.UserService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenService jwtTokenService;
    private final UserService userService;

    public AuthController(AuthenticationManager authenticationManager,
            JwtTokenService jwtTokenService, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenService = jwtTokenService;
        this.userService = userService;

    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserRegistrationDTO registrationDto) {
        try {
            // Check if user already exists
            if (userService.findByEmail(registrationDto.getEmail()).isPresent()) {
                return ResponseEntity
                        .badRequest()
                        .body(Map.of("message", "Error: Email is already in use!"));
            }
            // Create new user
            User user = userService.registerUser(registrationDto);

            return ResponseEntity.ok(Map.of(
                    "message", "User registered successfully!",
                    "userId", user.getId()));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Error during registration: " + e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()));

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String token = jwtTokenService.generateToken(userDetails);

        return ResponseEntity.ok(new LoginResponse(token));
    }

}