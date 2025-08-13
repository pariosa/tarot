package com.mercy.tarot.controllers;

import java.util.Map;

import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mercy.tarot.dto.ForgotPasswordRequest;
import com.mercy.tarot.dto.LoginRequest;
import com.mercy.tarot.dto.LoginResponse;
import com.mercy.tarot.dto.ResetPasswordRequest;
import com.mercy.tarot.dto.UserRegistrationDTO;
import com.mercy.tarot.models.User;
import com.mercy.tarot.service.JwtTokenService;
import com.mercy.tarot.service.PasswordResetService;
import com.mercy.tarot.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenService jwtTokenService;
    private final UserService userService;
    private final PasswordResetService passwordResetService;

    Logger logger = org.slf4j.LoggerFactory.getLogger(AuthController.class);

    public AuthController(AuthenticationManager authenticationManager,
            JwtTokenService jwtTokenService, UserService userService,
            PasswordResetService passwordResetService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenService = jwtTokenService;
        this.userService = userService;
        this.passwordResetService = passwordResetService;

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

    @GetMapping("/validate")
    public ResponseEntity<?> validateToken(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@Valid @RequestBody ForgotPasswordRequest request) {
        try {
            passwordResetService.initiatePasswordReset(request.getEmail());

            // Always return success to prevent email enumeration attacks
            return ResponseEntity.ok(Map.of(
                    "message",
                    "If the email address exists in our system, you will receive password reset instructions."));

        } catch (Exception e) {
            logger.error("Error processing forgot password request for email: {}", request.getEmail(), e);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "An error occurred while processing your request. Please try again."));
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@Valid @RequestBody ResetPasswordRequest request) {
        try {
            boolean success = passwordResetService.resetPassword(request.getToken(), request.getNewPassword());

            if (success) {
                return ResponseEntity.ok(Map.of(
                        "message", "Password has been reset successfully. You can now log in with your new password."));
            } else {
                return ResponseEntity
                        .badRequest()
                        .body(Map.of("message",
                                "Invalid or expired reset token. Please request a new password reset."));
            }

        } catch (Exception e) {
            logger.error("Error resetting password with token: {}", request.getToken(), e);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "An error occurred while resetting your password. Please try again."));
        }
    }

    @GetMapping("/validate-reset-token")
    public ResponseEntity<?> validateResetToken(@RequestParam String token) {
        try {
            boolean valid = passwordResetService.validateResetToken(token);

            if (valid) {
                return ResponseEntity.ok(Map.of(
                        "valid", true,
                        "message", "Token is valid"));
            } else {
                return ResponseEntity
                        .badRequest()
                        .body(Map.of(
                                "valid", false,
                                "message", "Invalid or expired token"));
            }

        } catch (Exception e) {
            logger.error("Error validating reset token: {}", token, e);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                            "valid", false,
                            "message", "An error occurred while validating the token"));
        }
    }
}