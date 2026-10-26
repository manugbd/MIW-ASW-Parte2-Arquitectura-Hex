package com.miw.feature.shoppingcart.core.port.outbound.persistence;

import java.util.Optional;

import com.miw.feature.shoppingcart.core.domain.ShoppingCart;

public interface ShoppingCartRepository {

	Optional<ShoppingCart> findCartByUserId(int userId);

	void save(ShoppingCart shoppingCart);
}
