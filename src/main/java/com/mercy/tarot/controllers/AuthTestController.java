package com.mercy.tarot.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mercy.tarot.models.User;

@RestController
@RequestMapping("/api")
public class AuthTestController {

    @GetMapping("/public/hello")
    public ResponseEntity<Map<String, String>> publicHello() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Hello from public endpoint!");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/protected/hello")
    public ResponseEntity<Map<String, Object>> protectedHello(Authentication authentication) {
        User user = (User) authentication.getPrincipal();

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Hello from protected endpoint!");
        response.put("user", Map.of(
                "id", user.getId(),
                "email", user.getEmail(),
                "name", user.getName(),
                "role", user.getRole()));

        return ResponseEntity.ok(response);
    }

    @GetMapping("/admin/hello")
    public ResponseEntity<Map<String, String>> adminHello() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Hello from admin endpoint!");
        return ResponseEntity.ok(response);
    }
}