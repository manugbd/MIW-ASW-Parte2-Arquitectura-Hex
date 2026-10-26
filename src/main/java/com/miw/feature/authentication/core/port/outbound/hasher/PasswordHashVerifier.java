package com.miw.feature.authentication.core.port.outbound.hasher;

public interface PasswordHashVerifier {

	boolean matches(String password, String hash);
}
