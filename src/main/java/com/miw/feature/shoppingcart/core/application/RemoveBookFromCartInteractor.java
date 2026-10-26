package com.miw.feature.shoppingcart.core.application;

import com.miw.feature.shoppingcart.core.domain.ShoppingCart;
import com.miw.feature.shoppingcart.core.port.inbound.RemoveBookFromCart;
import com.miw.feature.shoppingcart.core.port.outbound.persistence.ShoppingCartRepository;

public class RemoveBookFromCartInteractor implements RemoveBookFromCart {

	private final ShoppingCartRepository shoppingCartRepository;

	public RemoveBookFromCartInteractor(
			ShoppingCartRepository shoppingCartRepository) {

		this.shoppingCartRepository = shoppingCartRepository;
	}

	@Override
	public void removeBook(int userId, int bookId) {
		ShoppingCart shoppingCart = shoppingCartRepository
				.findCartByUserId(userId)
				.orElse(null);

		if (shoppingCart == null) {
			return;
		}

		shoppingCart.removeBook(bookId);
		shoppingCartRepository.save(shoppingCart);
	}
}
