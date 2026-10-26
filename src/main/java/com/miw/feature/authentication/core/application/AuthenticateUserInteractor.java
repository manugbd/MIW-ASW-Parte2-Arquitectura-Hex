package com.miw.feature.authentication.core.application;

import java.util.Optional;

import com.miw.feature.authentication.core.domain.User;
import com.miw.feature.authentication.core.port.inbound.AuthenticateUser;
import com.miw.feature.authentication.core.port.inbound.AuthenticatedUser;
import com.miw.feature.authentication.core.port.outbound.hasher.PasswordHashVerifier;
import com.miw.feature.authentication.core.port.outbound.persistence.UserRepository;

public class AuthenticateUserInteractor implements AuthenticateUser {

	private final UserRepository userRepository;
	private final PasswordHashVerifier passwordHasher;

	public AuthenticateUserInteractor(UserRepository userRepository, PasswordHashVerifier passwordHasher) {

		this.userRepository = userRepository;
		this.passwordHasher = passwordHasher;
	}

	@Override
	public Optional<AuthenticatedUser> authenticate(String username, String password) {

		Optional<User> userOptional = userRepository.findByUsername(username);

		if (userOptional.isEmpty()) {
			return Optional.empty();
		}

		User user = userOptional.get();

		if (!passwordHasher.matches(password, user.getPassword())) {

			return Optional.empty();
		}

		return Optional.of(new AuthenticatedUser(user.getId(), user.getUsername(), user.getRole()));
	}
}
