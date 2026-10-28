package com.miw.domain;

import com.miw.feature.authentication.core.domain.Role;
import com.miw.feature.authentication.core.domain.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "\t\n"})
    void rejectsMissingUsernameAndPassword(String invalid) {
        assertThrows(IllegalArgumentException.class, () -> new User(1, invalid, "hash", Role.CUSTOMER));
        assertThrows(IllegalArgumentException.class, () -> new User(1, "customer", invalid, Role.CUSTOMER));
        User user = new User(1, "customer", "hash", Role.CUSTOMER);
        assertThrows(IllegalArgumentException.class, () -> user.setUsername(invalid));
        assertThrows(IllegalArgumentException.class, () -> user.setPassword(invalid));
        assertEquals("customer", user.getUsername());
        assertEquals("hash", user.getPassword());
    }

    @Test
    void rejectsNegativeIdAndMissingRole() {
        assertThrows(IllegalArgumentException.class, () -> new User(-1, "user", "hash", Role.CUSTOMER));
        assertThrows(IllegalArgumentException.class, () -> new User(1, "user", "hash", null));
        User user = new User(0, "user", "hash", Role.CUSTOMER);
        assertThrows(IllegalArgumentException.class, () -> user.setRole(null));
        assertEquals(Role.CUSTOMER, user.getRole());
    }

    @Test
    void allowsChangingCredentialsAndRole() {
        User user = new User(1, "customer", "oldHash", Role.CUSTOMER);
        user.setUsername("admin");
        user.setPassword("newHash");
        user.setRole(Role.ADMIN);
        assertEquals("admin", user.getUsername());
        assertEquals("newHash", user.getPassword());
        assertEquals(Role.ADMIN, user.getRole());
    }
}
