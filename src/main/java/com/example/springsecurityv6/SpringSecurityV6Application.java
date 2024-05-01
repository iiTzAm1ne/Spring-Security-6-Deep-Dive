package com.example.springsecurityv6;

import com.example.springsecurityv6.dto.RegisterRequest;
import com.example.springsecurityv6.Models.User;
import com.example.springsecurityv6.Repositories.UserRepository;
import com.example.springsecurityv6.Services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Optional;

@SpringBootApplication
@RequiredArgsConstructor

public class SpringSecurityV6Application {

	private final AuthenticationService authenticationService;
	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityV6Application.class, args);
	}


	@Bean
	CommandLineRunner createAdminAccount(UserRepository userRepository){
		return args -> {
		Optional<User> u =	userRepository.findByEmail("admin@admin.com");

		if(!u.isPresent()){
			RegisterRequest request = new RegisterRequest();
			request.setEmail("admin@admin.com");
			request.setPassword("admin");
			request.setFullName("amdin");
			request.setKey("this_is_admin");
			User user = authenticationService.register(request);
		}


		};
	}

}
