package com.miw.feature.shoppingcart.adapter.outbound.persistence;

import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import com.miw.feature.shoppingcart.core.domain.ShoppingCart;
import com.miw.feature.shoppingcart.core.port.outbound.persistence.ShoppingCartRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

public class JpaShoppingCartRepositoryAdapter
		implements ShoppingCartRepository {

	private final EntityManager entityManager;

	public JpaShoppingCartRepositoryAdapter(
			EntityManager entityManager) {

		this.entityManager = entityManager;
	}

	@Override
	public Optional<ShoppingCart> findCartByUserId(int userId) {

		String jpql = "select c from ShoppingCart c "
				+ "where c.userId = :userId";

		TypedQuery<ShoppingCart> query = entityManager
				.createQuery(jpql, ShoppingCart.class);

		query.setParameter("userId", userId);

		return query.getResultStream().findFirst();
	}

	@Override
	@Transactional
	public void save(ShoppingCart shoppingCart) {

		if (shoppingCart.getId() == 0) {
			entityManager.persist(shoppingCart);
		} else {
			entityManager.merge(shoppingCart);
		}
	}
}
