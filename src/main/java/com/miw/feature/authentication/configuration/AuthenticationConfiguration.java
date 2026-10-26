package com.miw.feature.authentication.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.miw.feature.authentication.adapter.outbound.hasher.BCryptPasswordHashVerifierAdapter;
import com.miw.feature.authentication.adapter.outbound.persistence.JpaUserRepositoryAdapter;
import com.miw.feature.authentication.core.application.AuthenticateUserInteractor;
import com.miw.feature.authentication.core.port.inbound.AuthenticateUser;
import com.miw.feature.authentication.core.port.outbound.hasher.PasswordHashVerifier;
import com.miw.feature.authentication.core.port.outbound.persistence.UserRepository;

import jakarta.persistence.EntityManager;

@Configuration
public class AuthenticationConfiguration {

	@Bean
	public UserRepository userRepository(EntityManager entityManager) {
		return new JpaUserRepositoryAdapter(entityManager);
	}

	@Bean
	public PasswordHashVerifier passwordHashVerifier() {
		return new BCryptPasswordHashVerifierAdapter();
	}

	@Bean
	public AuthenticateUser authenticateUser(UserRepository userRepository, PasswordHashVerifier passwordHashVerifier) {

		return new AuthenticateUserInteractor(userRepository, passwordHashVerifier);
	}

}