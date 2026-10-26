package com.miw.feature.authentication.core.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.miw.feature.authentication.core.domain.Role;
import com.miw.feature.authentication.core.domain.User;
import com.miw.feature.authentication.core.port.inbound.AuthenticatedUser;
import com.miw.feature.authentication.core.port.outbound.hasher.PasswordHashVerifier;
import com.miw.feature.authentication.core.port.outbound.persistence.UserRepository;

class AuthenticateUserInteractorTest {

	private UserRepository userRepository;
	private PasswordHashVerifier passwordHasher;
	private AuthenticateUserInteractor interactor;

	private User adminUser;
	private User customerUser;

	@BeforeEach
	void setUp() {

		userRepository = mock(UserRepository.class);
		passwordHasher = mock(PasswordHashVerifier.class);

		adminUser = new User(1, "admin", "storedHash", Role.ADMIN);

		customerUser = new User(2, "customer", "storedHash", Role.CUSTOMER);

		when(userRepository.findByUsername("admin")).thenReturn(Optional.of(adminUser));

		when(userRepository.findByUsername("customer")).thenReturn(Optional.of(customerUser));

		when(userRepository.findByUsername("nouser")).thenReturn(Optional.empty());

		when(passwordHasher.matches("goodPassword", "storedHash")).thenReturn(true);

		when(passwordHasher.matches("wrongPassword", "storedHash")).thenReturn(false);

		interactor = new AuthenticateUserInteractor(userRepository, passwordHasher);
	}

	@Test
	void authenticateValidAdmin() {

		// Act
		Optional<AuthenticatedUser> result = interactor.authenticate("admin", "goodPassword");

		// Assert
		assertTrue(result.isPresent());

		assertEquals(1, result.get().id());
		assertEquals("admin", result.get().username());
		assertEquals(Role.ADMIN, result.get().role());
	}

	@Test
	void authenticateValidCustomer() {

		// Act
		Optional<AuthenticatedUser> result = interactor.authenticate("customer", "goodPassword");

		// Assert
		assertTrue(result.isPresent());

		assertEquals(2, result.get().id());
		assertEquals("customer", result.get().username());
		assertEquals(Role.CUSTOMER, result.get().role());
	}

	@Test
	void rejectUnknownUser() {

		// Act
		Optional<AuthenticatedUser> result = interactor.authenticate("nouser", "goodPassword");

		// Assert
		assertTrue(result.isEmpty());
	}

	@Test
	void rejectWrongPassword() {

		// Act
		Optional<AuthenticatedUser> result = interactor.authenticate("admin", "wrongPassword");

		// Assert
		assertTrue(result.isEmpty());
	}
}