package com.mercy.tarot;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.DefaultApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.mercy.tarot.runners.DatabaseSeederRunner;

@SpringBootApplication
@ComponentScan(basePackages = "com.mercy.tarot") // Ensure this covers your filter package
@EnableJpaRepositories(basePackages = {
		"com.mercy.tarot.repositories",
		"com.mercy.tarot.models",
		"com.mercy.tarot.runners",
		"com.mercy.tarot.seeders",
		"com.mercy.tarot.config",
		"com.mercy.tarot.helpers" })
@EntityScan(basePackages = "com.mercy.tarot.models")

public class TarotApplication {

	public static void main(String[] args) {
		System.out.println("Hello World! im that tarot boi");
		SpringApplication.run(TarotApplication.class, args);
	}

	public CommandLineRunner seedDatabase(DatabaseSeederRunner databaseSeederRunner) {
		return args -> {
			ApplicationArguments appArgs = new DefaultApplicationArguments(args);
			// Call the seedDatabase method from the DatabaseSeeder bean
			databaseSeederRunner.run(appArgs);
		};
	}
}
