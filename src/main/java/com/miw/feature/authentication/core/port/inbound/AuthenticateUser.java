package com.miw.feature.authentication.core.port.inbound;

import java.util.Optional;

public interface AuthenticateUser {

	Optional<AuthenticatedUser> authenticate(String username, String password);
}
