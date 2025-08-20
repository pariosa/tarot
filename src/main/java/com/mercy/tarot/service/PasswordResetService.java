package com.mercy.tarot.service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mercy.tarot.exceptions.ResourceNotFoundException;
import com.mercy.tarot.models.PasswordResetToken;
import com.mercy.tarot.models.User;
import com.mercy.tarot.repositories.PasswordResetTokenRepository;

@Service
@Transactional
public class PasswordResetService {

    private static final Logger logger = LoggerFactory.getLogger(PasswordResetService.class);
    private static final int TOKEN_VALIDITY_HOURS = 1;

    private final PasswordResetTokenRepository tokenRepository;
    private final UserService userService;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;
    private final SecureRandom secureRandom = new SecureRandom();

    public PasswordResetService(
            PasswordResetTokenRepository tokenRepository,
            UserService userService,
            EmailService emailService,
            PasswordEncoder passwordEncoder) {
        this.tokenRepository = tokenRepository;
        this.userService = userService;
        this.emailService = emailService;
        this.passwordEncoder = passwordEncoder;
    }

    public void initiatePasswordReset(String email) {
        Optional<User> userOptional = userService.findByEmail(email);

        if (userOptional.isEmpty()) {
            // Don't reveal whether email exists or not for security
            logger.warn("Password reset attempted for non-existent email: {}", email);
            return;
        }

        User user = userOptional.get();

        // Delete any existing tokens for this user
        tokenRepository.deleteByUser(user);

        // Generate secure random token
        String token = generateSecureToken();
        LocalDateTime expiryDate = LocalDateTime.now().plusHours(TOKEN_VALIDITY_HOURS);

        // Save token
        PasswordResetToken resetToken = new PasswordResetToken(token, user, expiryDate);
        tokenRepository.save(resetToken);

        // Send email
        emailService.sendPasswordResetEmail(user.getEmail(), token);

        logger.info("Password reset initiated for user: {}", user.getEmail());
    }

    public String createPasswordResetTokenForAuthenticatedUser(String email) {
        Optional<User> userOptional = userService.findByEmail(email);

        if (userOptional.isEmpty()) {
            throw new ResourceNotFoundException("User not found with email: " + email);
        }

        User user = userOptional.get();

        // Delete any existing tokens for this user
        tokenRepository.deleteByUser(user);

        // Generate secure random token
        String token = generateSecureToken();
        LocalDateTime expiryDate = LocalDateTime.now().plusHours(TOKEN_VALIDITY_HOURS);

        // Save token
        PasswordResetToken resetToken = new PasswordResetToken(token, user, expiryDate);
        tokenRepository.save(resetToken);

        logger.info("Password reset token generated for authenticated user: {}", user.getEmail());
        return token;
    }

    public boolean resetPassword(String token, String newPassword) {
        Optional<PasswordResetToken> tokenOptional = tokenRepository.findByToken(token);

        if (tokenOptional.isEmpty()) {
            logger.warn("Invalid password reset token attempted: {}", token);
            return false;
        }

        PasswordResetToken resetToken = tokenOptional.get();

        if (!resetToken.isValid()) {
            logger.warn("Expired or used password reset token attempted: {}", token);
            return false;
        }

        // Update user's password
        User user = resetToken.getUser();
        user.setPassword(passwordEncoder.encode(newPassword));
        userService.updateUser(convertToProfileDTO(user));

        // Mark token as used
        resetToken.setUsed(true);
        tokenRepository.save(resetToken);

        logger.info("Password successfully reset for user: {}", user.getEmail());
        return true;
    }

    public boolean validateResetToken(String token) {
        Optional<PasswordResetToken> tokenOptional = tokenRepository.findByToken(token);
        return tokenOptional.isPresent() && tokenOptional.get().isValid();
    }

    public String generateSecureToken() {
        byte[] tokenBytes = new byte[32];
        secureRandom.nextBytes(tokenBytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(tokenBytes);
    }

    private com.mercy.tarot.dto.UserProfileDTO convertToProfileDTO(User user) {
        com.mercy.tarot.dto.UserProfileDTO dto = new com.mercy.tarot.dto.UserProfileDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        return dto;
    }

    // Clean up expired tokens daily at 2 AM
    @Scheduled(cron = "0 0 2 * * ?")
    public void cleanupExpiredTokens() {
        tokenRepository.deleteExpiredTokens(LocalDateTime.now());
        logger.info("Cleaned up expired password reset tokens");
    }
}