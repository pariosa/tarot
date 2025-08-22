package com.mercy.tarot.controllers;

import java.time.LocalDateTime;
import java.util.Map;

import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
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
import com.mercy.tarot.dto.PasswordResetTokenResponse;
import com.mercy.tarot.dto.ResetPasswordRequest;
import com.mercy.tarot.dto.UserRegistrationDTO;
import com.mercy.tarot.exceptions.ResourceNotFoundException;
import com.mercy.tarot.models.PasswordResetToken;
import com.mercy.tarot.models.User;
import com.mercy.tarot.repositories.PasswordResetTokenRepository;
import com.mercy.tarot.service.JwtTokenService;
import com.mercy.tarot.service.PasswordResetService;
import com.mercy.tarot.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenService jwtTokenService;
    private final UserService userService;
    private final PasswordResetService passwordResetService;
    private final PasswordResetTokenRepository passwordResetTokenRepository;

    Logger logger = org.slf4j.LoggerFactory.getLogger(AuthController.class);

    public AuthController(AuthenticationManager authenticationManager,
            JwtTokenService jwtTokenService, UserService userService,
            PasswordResetService passwordResetService, PasswordResetTokenRepository passwordResetTokenRepository) {

        this.authenticationManager = authenticationManager;
        this.jwtTokenService = jwtTokenService;
        this.userService = userService;
        this.passwordResetService = passwordResetService;
        this.passwordResetTokenRepository = passwordResetTokenRepository;
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

    @PostMapping("/issue-authed-user-password-reset-token")
    public ResponseEntity<PasswordResetTokenResponse> issueAuthedUserPasswordResetToken(
            Authentication authentication, @RequestParam String email) {

        // Get the username from authentication
        String username = authentication.getName();

        // Verify the authenticated user matches the requested email
        User currentUser = userService.findByEmail(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (!currentUser.getEmail().equals(email)) {
            throw new AccessDeniedException("You can only request tokens for your own account");
        }

        // Generate secure token using your existing service
        String resetToken = passwordResetService.generateSecureToken();

        // Create expiry date (matching your service's logic)
        LocalDateTime expiryDate = LocalDateTime.now().plusHours(1);

        // Delete any existing tokens for this user
        passwordResetTokenRepository.deleteByUser(currentUser);

        // Create and save the password reset token
        PasswordResetToken tokenEntity = new PasswordResetToken(resetToken, currentUser, expiryDate);
        passwordResetTokenRepository.save(tokenEntity);

        // Return the token response
        return ResponseEntity.ok(new PasswordResetTokenResponse(resetToken, expiryDate));
    }

    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(
            Authentication authentication,
            HttpServletRequest request,
            @RequestBody(required = false) Map<String, String> requestBody,
            @RequestParam(required = false) String token,
            @RequestParam(required = false) String currentPassword,
            @RequestParam(required = false) String newPassword) {

        logger.info("=== DEBUG CHANGE PASSWORD ===");
        logger.info("Authentication: {}", authentication != null ? authentication.getName() : "null");
        logger.info("Request body: {}", requestBody);
        logger.info("Query params - token: {}, currentPassword: {}, newPassword: {}",
                token, currentPassword != null ? "[PRESENT]" : "null",
                newPassword != null ? "[PRESENT]" : "null");
        logger.info("Authorization header: {}", request.getHeader("Authorization"));
        logger.info("Request URI: {}", request.getRequestURI());
        logger.info("============================");

        if (authentication == null || !authentication.isAuthenticated()) {
            logger.error("User not authenticated");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "User not authenticated"));
        }

        try {
            String userEmail = authentication.getName();
            logger.info("Authenticated user: {}", userEmail);

            // For now, just return success to verify authentication works
            return ResponseEntity.ok(Map.of(
                    "message", "Authentication successful",
                    "user", userEmail,
                    "hasRequestBody", requestBody != null,
                    "hasQueryParams", token != null || currentPassword != null || newPassword != null));

        } catch (Exception e) {
            logger.error("Error in change password", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
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