package com.miw.feature.authentication.core.port.outbound.persistence;

import java.util.Optional;

import com.miw.feature.authentication.core.domain.User;

public interface UserRepository {
	Optional<User> findByUsername(String username);
}