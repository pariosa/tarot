package com.mercy.tarot.seeders;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.mercy.tarot.config.roles.Roles;
import com.mercy.tarot.models.User;
import com.mercy.tarot.repositories.UserRepository;

@Component
public class UserSeeder implements CommandLineRunner {

    private final UserRepository userRepository;

    public UserSeeder(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.count() == 0) {
            // Regular user
            User user = new User("regular@example.com", "abc123456!", "Regular User");
            user.setPhotoUrl("https://example.com/user.jpg");
            userRepository.save(user);

            // Premium user
            User premiumUser = new User("premium@gmail.com", "preemiepassword1", "Premium User");
            premiumUser.addRole(Roles.PREMIUM);
            premiumUser.setPhotoUrl("https://example.com/premium.jpg");
            userRepository.save(premiumUser);

            // Admin user
            User admin = new User("admin@example.com", "admin123456", "Admin User");
            admin.addRole(Roles.ADMIN);
            admin.setPhotoUrl("https://example.com/admin.jpg");
            userRepository.save(admin);
        }
    }
}