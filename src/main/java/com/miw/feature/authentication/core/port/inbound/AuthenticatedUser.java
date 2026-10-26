package com.miw.feature.authentication.core.port.inbound;

import com.miw.feature.authentication.core.domain.Role;

public record AuthenticatedUser(int id, String username, Role role) {
}
