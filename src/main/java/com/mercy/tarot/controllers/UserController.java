package com.mercy.tarot.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mercy.tarot.dto.UserRegistrationDTO;
import com.mercy.tarot.models.User;
import com.mercy.tarot.repositories.UserRepository;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody UserRegistrationDTO registrationDto) {
        // Check if user already exists
        if (userRepository.findByFirebaseUid(registrationDto.getFirebaseUid()).isPresent()) {
            return ResponseEntity.badRequest().build();
        }

        User user = new User();
        user.setFirebaseUid(registrationDto.getFirebaseUid());
        user.setEmail(registrationDto.getEmail());
        user.setName(registrationDto.getName());
        user.setIsActive(true);

        User savedUser = userRepository.save(user);
        return ResponseEntity.ok(savedUser);
    }

    @GetMapping("/{firebaseUid}")
    public ResponseEntity<User> getUser(@PathVariable String firebaseUid) {
        return userRepository.findByFirebaseUid(firebaseUid)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}