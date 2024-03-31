package com.mercy.tarot.runners;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.mercy.tarot.seeders.DatabaseSeeder;

@Component
public class DatabaseSeederRunner implements ApplicationRunner {

    private final DatabaseSeeder databaseSeeder;

    public DatabaseSeederRunner(DatabaseSeeder databaseSeeder) {
        this.databaseSeeder = databaseSeeder;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        databaseSeeder.seedDatabase(); // Trigger seeding of the database
    }
}