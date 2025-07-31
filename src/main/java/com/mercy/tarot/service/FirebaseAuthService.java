package com.mercy.tarot.service;

import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.mercy.tarot.models.User;
import com.mercy.tarot.repositories.UserRepository;

@Service
public class FirebaseAuthService {

    private static final Logger log = LoggerFactory.getLogger(FirebaseAuthService.class);

    private final FirebaseAuth firebaseAuth;
    private final UserRepository userRepository;

    @Autowired
    public FirebaseAuthService(FirebaseAuth firebaseAuth, UserRepository userRepository) {
        this.firebaseAuth = firebaseAuth;
        this.userRepository = userRepository;
    }

    /**
     * Verify Firebase ID token and return user details
     */
    public User verifyTokenAndGetUser(String idToken) {
        try {
            // Verify the token with Firebase
            FirebaseToken decodedToken = firebaseAuth.verifyIdToken(idToken);

            String uid = decodedToken.getUid();
            String email = decodedToken.getEmail();
            String name = decodedToken.getName();
            String picture = decodedToken.getPicture();

            log.info("Successfully verified Firebase token for user: {}", email);

            // Get or create user in local database
            User user = getOrCreateUser(uid, email, name, picture);

            return user;

        } catch (FirebaseAuthException e) {
            log.error("Failed to verify Firebase token: {}", e.getMessage());
            throw new RuntimeException("Invalid Firebase token: " + e.getMessage(), e);
        }
    }

    /**
     * Get existing user or create new one
     */
    private User getOrCreateUser(String firebaseUid, String email, String name, String photoUrl) {
        Optional<User> existingUser = userRepository.findByFirebaseUid(firebaseUid);

        if (existingUser.isPresent()) {
            User user = existingUser.get();

            // Update user info if it has changed
            boolean hasChanges = false;

            if (!Objects.equals(user.getEmail(), email)) {
                user.setEmail(email);
                hasChanges = true;
            }

            if (!Objects.equals(user.getName(), name)) {
                user.setName(name);
                hasChanges = true;
            }

            if (!Objects.equals(user.getPhotoUrl(), photoUrl)) {
                user.setPhotoUrl(photoUrl);
                hasChanges = true;
            }

            if (hasChanges) {
                user = userRepository.save(user);
                log.info("Updated existing user: {}", email);
            }

            return user;

        } else {
            // Create new user
            User newUser = new User(firebaseUid, email, name);
            newUser.setPhotoUrl(photoUrl);

            User savedUser = userRepository.save(newUser);
            log.info("Created new user: {}", email);

            return savedUser;
        }
    }
}