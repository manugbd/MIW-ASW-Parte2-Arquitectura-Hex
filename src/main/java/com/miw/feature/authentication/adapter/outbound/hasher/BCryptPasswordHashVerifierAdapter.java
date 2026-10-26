package com.miw.feature.authentication.adapter.outbound.hasher;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.miw.feature.authentication.core.port.outbound.hasher.PasswordHashVerifier;

public class BCryptPasswordHashVerifierAdapter implements PasswordHashVerifier {

	private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

	@Override
	public boolean matches(String password, String hash) {

		return encoder.matches(password, hash);
	}
}