package com.mercy.tarot.controllers;

import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mercy.tarot.dto.UserProfileDTO;
import com.mercy.tarot.exceptions.ResourceNotFoundException;
import com.mercy.tarot.models.User;
import com.mercy.tarot.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    private final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(UserController.class);

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<UserProfileDTO> getCurrentUser(Authentication authentication) {
        try {
            if (authentication == null || !authentication.isAuthenticated()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            // Get the username from authentication
            String username = authentication.getName();

            // Load the full user details from your service
            User currentUser = userService.findByEmail(username)
                    .orElseThrow(() -> new ResourceNotFoundException("User not found"));

            UserProfileDTO profileDto = new UserProfileDTO(
                    currentUser.getId(),
                    currentUser.getName(),
                    currentUser.getEmail(),
                    currentUser.getRoles().stream()
                            .map(Enum::name)
                            .collect(Collectors.toSet()));

            return ResponseEntity.ok(profileDto);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            logger.error("Error getting current user", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/me")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<UserProfileDTO> updateCurrentUser(
            Authentication authentication,
            @RequestBody UserProfileDTO updateDto) {
        try {
            User updatedUser = userService.updateUser(updateDto).orElse(null);

            UserProfileDTO profileDto = new UserProfileDTO(
                    updatedUser.getId(),
                    updatedUser.getName(),
                    updatedUser.getEmail(),
                    updatedUser.getRoles().stream().map(Enum::name).collect(Collectors.toSet()));
            return ResponseEntity.ok(profileDto);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/check-email")
    public ResponseEntity<Map<String, Boolean>> checkEmailExists(@RequestParam String email) {
        boolean exists = userService.findByEmail(email).isPresent();
        return ResponseEntity.ok(Map.of("exists", exists));
    }

    @PostMapping("/{userId}/roles")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addRole(@PathVariable Long userId, @RequestBody String roleName) {
        try {
            User updatedUser = userService.addRoleToUser(userId, roleName);
            return ResponseEntity.ok(updatedUser);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN') or (hasRole('USER') and @userService.isCurrentUser(authentication.principal, #userId))")
    public ResponseEntity<UserProfileDTO> getUserById(@PathVariable Long userId) {
        try {
            User user = userService.findById(userId)
                    .orElseThrow(() -> new ResourceNotFoundException("User not found"));

            UserProfileDTO profileDto = new UserProfileDTO(
                    user.getId(),
                    user.getName(),
                    user.getEmail(),
                    user.getRoles().stream().map(Enum::name).collect(Collectors.toSet()));
            return ResponseEntity.ok(profileDto);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}