package com.miw.feature.shoppingcart.core.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class CartItem {

    // Attributes

    @Column(name = "BOOK_ID", nullable = false)
    private int bookId;

    @Column(name = "QUANTITY", nullable = false)
    private int quantity;

    // Empty constructor

    protected CartItem() {    }

    // All args constructor

    public CartItem(int bookId, int quantity) {
        setBookId(bookId);
        setQuantity(quantity);
    }

    // Getters


    public int getBookId() {
        return bookId;
    }

    public int getQuantity() {
        return quantity;
    }

    // Setters

    private void setBookId(int bookId) {
        if (bookId <= 0) throw new IllegalArgumentException("Book ID must be positive");
        this.bookId = bookId;
    }

    private void setQuantity(int quantity) {
        if (quantity < 1) throw new IllegalArgumentException("Quantity must be at least 1");
        this.quantity = quantity;
    }

    // Business methods

    public void increaseQuantity() {
        setQuantity(getQuantity() + 1);
    }

    public void decreaseQuantity() {
        if (getQuantity() <= 1) throw new IllegalStateException("Quantity cannot be lower than 1");

        setQuantity(getQuantity() - 1);
    }

    // Equals and hashCode

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        return object instanceof CartItem other
                && Objects.equals(getBookId(), other.getBookId())
                && getQuantity() == other.getQuantity();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getBookId(), getQuantity());
    }
}
