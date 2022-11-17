package com.kosavpa.first.boot.example;

import java.util.Collections;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.kosavpa.first.boot.example.model.entity.users.RoleEntity;
import com.kosavpa.first.boot.example.model.entity.users.UserEntity;
import com.kosavpa.first.boot.example.model.repository.RoleRepository;
import com.kosavpa.first.boot.example.model.repository.UserRepository;

@SpringBootApplication
public class DemoApplication {
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	public ApplicationRunner dataLoader(
		UserRepository userRepo, PasswordEncoder encoder, RoleRepository roleRepository) {
			return args -> {
				if (userRepo.findByUsername("ADMIN") == null && roleRepository.findById(1l).orElse(null) == null && roleRepository.findById(2l).orElse(null) == null){
					userRepo.save(new UserEntity(
										"ADMIN",
										encoder.encode("pwd"),
										Collections.singleton(new RoleEntity(2l, "ROLE_ADMIN"))));
					roleRepository.save(new RoleEntity(1L, "ROLE_USER"));
				}
		};
	}
}