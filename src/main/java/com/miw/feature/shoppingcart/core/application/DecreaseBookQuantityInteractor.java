package com.miw.feature.shoppingcart.core.application;

import com.miw.feature.shoppingcart.core.domain.ShoppingCart;
import com.miw.feature.shoppingcart.core.port.inbound.DecreaseBookQuantity;
import com.miw.feature.shoppingcart.core.port.outbound.persistence.ShoppingCartRepository;

public class DecreaseBookQuantityInteractor implements DecreaseBookQuantity {

	private final ShoppingCartRepository shoppingCartRepository;

	public DecreaseBookQuantityInteractor(
			ShoppingCartRepository shoppingCartRepository) {

		this.shoppingCartRepository = shoppingCartRepository;
	}

	@Override
	public void decreaseBookQuantity(int userId, int bookId) {
		ShoppingCart shoppingCart = shoppingCartRepository
				.findCartByUserId(userId)
				.orElse(null);

		if (shoppingCart == null) {
			return;
		}

		shoppingCart.decreaseBookQuantity(bookId);
		shoppingCartRepository.save(shoppingCart);
	}
}
