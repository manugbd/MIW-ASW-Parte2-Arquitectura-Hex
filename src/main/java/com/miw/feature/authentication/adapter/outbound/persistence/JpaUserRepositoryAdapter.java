package com.miw.feature.authentication.adapter.outbound.persistence;

import java.util.Optional;

import com.miw.feature.authentication.core.domain.User;
import com.miw.feature.authentication.core.port.outbound.persistence.UserRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

public class JpaUserRepositoryAdapter implements UserRepository {

	private final EntityManager entityManager;

	public JpaUserRepositoryAdapter(EntityManager entityManager) {

		this.entityManager = entityManager;
	}

	@Override
	public Optional<User> findByUsername(String username) {

		String jpql = "select u from User u " + "where u.username = :username";

		TypedQuery<User> query = entityManager.createQuery(jpql, User.class);

		query.setParameter("username", username);

		return query.getResultStream().findFirst();
	}
}