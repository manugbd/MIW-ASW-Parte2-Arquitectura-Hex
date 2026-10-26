package com.miw.event.listener;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.miw.feature.shoppingcart.core.port.outbound.event.BookAddedToCartEvent;

@Component
public class BookAddedToCartConsoleListener {

	@EventListener
	public void onBookAddedToCart(BookAddedToCartEvent event) {

		System.out.println("User " + event.userId() + " added book " + event.bookId() + " to the shopping cart.");
	}
}