package com.miw.feature.shoppingcart.core.domain;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "SHOPPING_CART")
public class ShoppingCart {

    // Attributes

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @Column(name = "USER_ID", nullable = false, unique = true)
    private int userId;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "SHOPPING_CART_ITEM",
            joinColumns = @JoinColumn(name = "SHOPPING_CART_ID"),
            uniqueConstraints = @UniqueConstraint(
                    name = "UK_CART_ITEM_BOOK",
                    columnNames = {"SHOPPING_CART_ID", "BOOK_ID"}
            )
    )
    private List<CartItem> cartItems = new ArrayList<>();

    // Empty constructor
    protected ShoppingCart() {
    }

    // All args constructor
    public ShoppingCart(int id, int userId, List<CartItem> cartItems) {
        setId(id);
        setUserId(userId);
        setCartItems(cartItems);
    }

    public ShoppingCart(int userId) {
        this(0, userId, List.of());
    }

    // Getters

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public List<CartItem> getCartItems() {
        return List.copyOf(cartItems);
    }

    // Setters

    private void setId(int id) {
        if (id < 0) throw new IllegalArgumentException("ID must not be negative");
        this.id = id;
    }

    private void setUserId(int userId) {
        if (userId < 0) throw new IllegalArgumentException("User ID must be positive");
        this.userId = userId;
    }

    private void setCartItems(List<CartItem> cartItems) {
        if (cartItems == null) throw new IllegalArgumentException("Cart items are required");
        Set<Integer> books = new HashSet<>();
        for (CartItem item : cartItems) {
            if (item == null || item.getBookId() < 0 || item.getQuantity() < 1 || !books.add(item.getBookId()))
                throw new IllegalArgumentException("Cart items must be valid and contain no duplicate books");
        }

        this.cartItems = new ArrayList<>();

        for (CartItem item : cartItems)
            this.cartItems.add(new CartItem(item.getBookId(), item.getQuantity()));
    }

    // Business methods

    public void addBook(int bookId) {
        if (bookId < 0) throw new IllegalArgumentException("Book ID must be positive or zero");

        CartItem item = cartItems.stream()
                .filter(candidate -> candidate.getBookId() == bookId)
                .findFirst().orElse(null);

        if (item == null)
            cartItems.add(new CartItem(bookId, 1));
        else item.increaseQuantity();
    }

    public void decreaseBookQuantity(int bookId) {
        CartItem item = findCartItem(bookId);
        if (item == null) return;

        if (item.getQuantity() == 1)
            cartItems.remove(item);
        else item.decreaseQuantity();

    }

    public void removeBook(int bookId) {
        CartItem item = findCartItem(bookId);

        if (item != null)
            cartItems.remove(item);
    }

    private CartItem findCartItem(int bookId) {
        if (bookId < 0) throw new IllegalArgumentException("Book ID must be positive");

        return cartItems.stream().filter(item -> item.getBookId() == bookId)
                .findFirst().orElse(null);
    }

    // Equals and hashCode

    @Override
    public final boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        return object instanceof ShoppingCart other && getId() != 0 && getId() == other.getId();
    }

    @Override
    public final int hashCode() {
        return ShoppingCart.class.hashCode();
    }
}
