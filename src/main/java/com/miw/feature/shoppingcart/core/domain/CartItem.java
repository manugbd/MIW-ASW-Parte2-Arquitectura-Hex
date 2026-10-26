package com.miw.feature.shoppingcart.core.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class CartItem {

	@Column(name = "BOOK_ID")
	private int bookId;

	@Column(name = "QUANTITY")
	private int quantity;

	protected CartItem() {
	}

	public CartItem(int bookId, int quantity) {
		if (quantity < 1) {
			throw new IllegalArgumentException("Quantity must be at least 1");
		}

		this.bookId = bookId;
		this.quantity = quantity;
	}

	public int getBookId() {
		return bookId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void increaseQuantity() {
		quantity++;
	}

	public void decreaseQuantity() {
		if (quantity <= 1) {
			throw new IllegalStateException("Quantity cannot be lower than 1");
		}

		quantity--;
	}
}
