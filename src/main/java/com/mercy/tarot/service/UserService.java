package com.mercy.tarot.service;

import java.util.Optional;

import com.mercy.tarot.dto.UserProfileDTO;
import com.mercy.tarot.dto.UserRegistrationDTO;
import com.mercy.tarot.models.User;

public interface UserService {
    Optional<User> findById(Long id);

    Optional<User> findByEmail(String email);

    User registerUser(UserRegistrationDTO registrationDto);

    Optional<User> updateUser(UserProfileDTO userProfileDTO);

    boolean validatePassword(User user, String rawPassword);

    User addRoleToUser(Long userId, String roleName);

}