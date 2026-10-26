package com.miw.feature.shoppingcart.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.miw.feature.shoppingcart.adapter.outbound.persistence.JpaShoppingCartRepositoryAdapter;
import com.miw.feature.shoppingcart.core.port.outbound.persistence.ShoppingCartRepository;

import jakarta.persistence.EntityManager;

@Configuration
public class ShoppingCartJpaConfiguration {

	@Bean
	public ShoppingCartRepository shoppingCartRepository(
			EntityManager entityManager) {

		return new JpaShoppingCartRepositoryAdapter(entityManager);
	}
}
