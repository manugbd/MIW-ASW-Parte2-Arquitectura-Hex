package com.miw.feature.shoppingcart.core.application;

import java.util.ArrayList;
import java.util.List;

import com.miw.feature.shoppingcart.core.domain.CartItem;
import com.miw.feature.shoppingcart.core.domain.ShoppingCart;
import com.miw.feature.shoppingcart.core.port.inbound.GetShoppingCart;
import com.miw.feature.shoppingcart.core.port.inbound.dto.CartItemDTO;
import com.miw.feature.shoppingcart.core.port.inbound.dto.ShoppingCartDTO;
import com.miw.feature.shoppingcart.core.port.outbound.catalog.BookCatalog;
import com.miw.feature.shoppingcart.core.port.outbound.catalog.BookForCartDTO;
import com.miw.feature.shoppingcart.core.port.outbound.persistence.ShoppingCartRepository;

public class GetShoppingCartInteractor implements GetShoppingCart {

	private final ShoppingCartRepository shoppingCartRepository;
	private final BookCatalog bookCatalog;

	public GetShoppingCartInteractor(
			ShoppingCartRepository shoppingCartRepository,
			BookCatalog bookCatalog) {

		this.shoppingCartRepository = shoppingCartRepository;
		this.bookCatalog = bookCatalog;
	}

	@Override
	public ShoppingCartDTO getShoppingCart(int userId) {
		ShoppingCart shoppingCart = shoppingCartRepository
				.findCartByUserId(userId)
				.orElseGet(() -> new ShoppingCart(userId));

		List<CartItemDTO> items = new ArrayList<>();
		double totalPrice = 0;

		for (CartItem cartItem : shoppingCart.getCartItems()) {
			BookForCartDTO book = bookCatalog
					.findBookById(cartItem.getBookId())
					.orElseThrow(() -> new IllegalStateException(
							"Book in cart does not exist"));

			double subtotal = book.price() * cartItem.getQuantity();

			items.add(new CartItemDTO(
					book.bookId(),
					book.title(),
					book.price(),
					cartItem.getQuantity(),
					subtotal));

			totalPrice += subtotal;
		}

		return new ShoppingCartDTO(items, totalPrice);
	}
}
