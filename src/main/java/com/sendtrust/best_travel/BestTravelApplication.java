package com.sendtrust.best_travel;

import com.sendtrust.best_travel.domain.repositories.mongo.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class BestTravelApplication implements CommandLineRunner {

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private AppUserRepository appUserRepository;

    public static void main(String[] args) {
		SpringApplication.run(BestTravelApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		this.appUserRepository.findAll()
				.forEach(user -> System.out.println(user.getUsername() + "-" + this.bCryptPasswordEncoder.encode(user.getPassword())));
	}
}
