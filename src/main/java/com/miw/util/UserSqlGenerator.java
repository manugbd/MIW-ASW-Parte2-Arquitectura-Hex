package com.miw.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.miw.feature.authentication.core.domain.Role;

public class UserSqlGenerator {

	private static final BCryptPasswordEncoder ENCODER = new BCryptPasswordEncoder();

	public static String createInsert(String username, String password, Role role) {

		String hash = ENCODER.encode(password);

		return """
				INSERT INTO APP_USER
				    (USERNAME, PASSWORD, ROLE)
				VALUES
				    ('%s', '%s', '%s');
				""".formatted(username, hash, role.name());
	}

	public static void main(String[] args) {

		System.out.println(createInsert("admin", "amazin", Role.ADMIN));
		System.out.println(createInsert("customer", "amazin", Role.CUSTOMER));
	}
}