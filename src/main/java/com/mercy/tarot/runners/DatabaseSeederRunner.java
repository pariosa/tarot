package com.mercy.tarot.runners;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.mercy.tarot.seeders.DatabaseSeeder;
import com.mercy.tarot.seeders.StoryElementSeeder;
import com.mercy.tarot.seeders.UserSeeder;

@Component
public class DatabaseSeederRunner implements ApplicationRunner {

    private final DatabaseSeeder databaseSeeder;
    private final StoryElementSeeder storyElementSeeder;
    private final UserSeeder userSeeder;

    public DatabaseSeederRunner(
            DatabaseSeeder databaseSeeder,
            StoryElementSeeder storyElementSeeder,
            UserSeeder userSeeder) {
        this.databaseSeeder = databaseSeeder;
        this.storyElementSeeder = storyElementSeeder;
        this.userSeeder = userSeeder;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        databaseSeeder.seedDatabase(); // Trigger seeding of the database
        storyElementSeeder.seedDatabase();
        userSeeder.run();
    }
}