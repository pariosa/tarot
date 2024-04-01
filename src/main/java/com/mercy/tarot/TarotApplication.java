package com.mercy.tarot;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.mercy.tarot.seeders.DatabaseSeeder;

@SpringBootApplication
@EnableJpaRepositories(basePackages = { "com.mercy.tarot.repositories", "com.mercy.tarot.models",
		"com.mercy.tarot.runners", "com.mercy.tarot.seeders", "com.mercy.tarot.config" })
@EntityScan(basePackages = "com.mercy.tarot.models")

public class TarotApplication {

	public static void main(String[] args) {
		System.out.println("Hello World! im that tarot boi");
		SpringApplication.run(TarotApplication.class, args);
	}

	public CommandLineRunner seedDatabase(DatabaseSeeder databaseSeeder) {
		return args -> {
			// Call the seedDatabase method from the DatabaseSeeder bean
			databaseSeeder.seedDatabase();
		};
	}
}
