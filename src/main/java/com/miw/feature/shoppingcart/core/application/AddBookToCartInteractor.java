package com.miw.feature.shoppingcart.core.application;

import com.miw.event.EventPublisher;
import com.miw.feature.shoppingcart.core.domain.ShoppingCart;
import com.miw.feature.shoppingcart.core.port.inbound.AddBookToCart;
import com.miw.feature.shoppingcart.core.port.outbound.catalog.BookCatalog;
import com.miw.feature.shoppingcart.core.port.outbound.event.BookAddedToCartEvent;
import com.miw.feature.shoppingcart.core.port.outbound.persistence.ShoppingCartRepository;

public class AddBookToCartInteractor implements AddBookToCart {

	private final ShoppingCartRepository shoppingCartRepository;
	private final BookCatalog bookCatalog;
	private final EventPublisher eventPublisher;

	public AddBookToCartInteractor(ShoppingCartRepository shoppingCartRepository, BookCatalog bookCatalog,
			EventPublisher eventPublisher) {

		this.shoppingCartRepository = shoppingCartRepository;
		this.bookCatalog = bookCatalog;
		this.eventPublisher = eventPublisher;
	}

	@Override
	public void addBook(int userId, int bookId) {
		if (bookCatalog.findBookById(bookId).isEmpty()) {
			throw new IllegalArgumentException("Book does not exist");
		}

		ShoppingCart shoppingCart = shoppingCartRepository.findCartByUserId(userId)
				.orElseGet(() -> new ShoppingCart(userId));

		shoppingCart.addBook(bookId);
		shoppingCartRepository.save(shoppingCart);

		eventPublisher.publish(new BookAddedToCartEvent(userId, bookId));
	}
}
