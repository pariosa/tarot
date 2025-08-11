package com.mercy.tarot.service;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mercy.tarot.config.roles.Roles;
import com.mercy.tarot.dto.UserProfileDTO;
import com.mercy.tarot.dto.UserRegistrationDTO;
import com.mercy.tarot.exceptions.ResourceNotFoundException;
import com.mercy.tarot.models.User;
import com.mercy.tarot.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository,
            PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;

    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User registerUser(UserRegistrationDTO registrationDto) {
        User user = new User();
        user.setEmail(registrationDto.getEmail());
        user.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        user.setName(registrationDto.getName());
        return userRepository.save(user);
    }

    @Override
    public User addRoleToUser(Long userId, String roleName) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        user.addRole(Roles.valueOf(roleName));
        return userRepository.save(user);
    }

    @Override
    public boolean validatePassword(User user, String rawPassword) {
        return passwordEncoder.matches(rawPassword, user.getPassword());
    }

    @Override
    public Optional<User> updateUser(UserProfileDTO userProfileDTO) {
        if (userProfileDTO == null || userProfileDTO.getId() == null) {
            return Optional.empty();
        }

        return userRepository.findById(userProfileDTO.getId())
                .map(user -> {
                    // Only update fields that are allowed to be updated
                    if (userProfileDTO.getName() != null) {
                        user.setName(userProfileDTO.getName());
                    }
                    if (userProfileDTO.getEmail() != null && !userProfileDTO.getEmail().equals(user.getEmail())) {
                        // Verify email is not already taken by another user
                        if (!userRepository.existsByEmailAndIdNot(userProfileDTO.getEmail(), user.getId())) {
                            user.setEmail(userProfileDTO.getEmail());
                        } else {
                            throw new IllegalArgumentException("Email is already in use by another account");
                        }
                    }

                    // Note: Roles should typically be updated through a separate admin endpoint
                    // So we're not updating roles here

                    return userRepository.save(user);
                });
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

}