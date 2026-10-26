package com.miw.feature.shoppingcart.core.domain;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "SHOPPING_CART")
public class ShoppingCart {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private int id;

	@Column(name = "USER_ID", nullable = false, unique = true)
	private int userId;

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(
			name = "SHOPPING_CART_ITEM",
			joinColumns = @JoinColumn(name = "SHOPPING_CART_ID"))
	private List<CartItem> cartItems = new ArrayList<>();

	protected ShoppingCart() {
	}

	public ShoppingCart(int id, int userId, List<CartItem> cartItems) {
		this.id = id;
		this.userId = userId;
		this.cartItems = new ArrayList<>(cartItems);
	}

	public ShoppingCart(int userId) {
		this(0, userId, List.of());
	}

	public int getId() {
		return id;
	}

	public int getUserId() {
		return userId;
	}

	public List<CartItem> getCartItems() {
		return List.copyOf(cartItems);
	}

	public void addBook(int bookId) {
		CartItem cartItem = findCartItem(bookId);

		if (cartItem == null) {
			cartItems.add(new CartItem(bookId, 1));
		} else {
			cartItem.increaseQuantity();
		}
	}

	public void decreaseBookQuantity(int bookId) {
		CartItem cartItem = findCartItem(bookId);

		if (cartItem == null) {
			return;
		}

		if (cartItem.getQuantity() == 1) {
			cartItems.remove(cartItem);
		} else {
			cartItem.decreaseQuantity();
		}
	}

	public void removeBook(int bookId) {
		cartItems.removeIf(cartItem -> cartItem.getBookId() == bookId);
	}

	private CartItem findCartItem(int bookId) {
		return cartItems.stream()
				.filter(cartItem -> cartItem.getBookId() == bookId)
				.findFirst()
				.orElse(null);
	}
}
