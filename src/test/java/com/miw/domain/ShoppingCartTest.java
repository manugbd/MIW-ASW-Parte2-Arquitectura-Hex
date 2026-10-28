package com.miw.domain;

import com.miw.feature.shoppingcart.core.domain.CartItem;
import com.miw.feature.shoppingcart.core.domain.ShoppingCart;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ShoppingCartTest {

    @Test
    void addingSameBookIncreasesQuantityWithoutCreatingAnotherLine() {
        ShoppingCart cart = new ShoppingCart(1);
        cart.addBook(10);
        cart.addBook(10);
        cart.addBook(20);
        assertEquals(2, cart.getCartItems().size());
        assertEquals(2, cart.getCartItems().getFirst().getQuantity());
        assertEquals(1, cart.getCartItems().getLast().getQuantity());
    }

    @Test
    void decreasingLastCopyRemovesTheLine() {
        ShoppingCart cart = new ShoppingCart(1);
        cart.addBook(10);
        cart.addBook(10);
        cart.decreaseBookQuantity(10);
        assertEquals(1, cart.getCartItems().getFirst().getQuantity());
        cart.decreaseBookQuantity(10);
        assertTrue(cart.getCartItems().isEmpty());
    }


    @Test
    void rejectsNegativeIdsAndInvalidInitialItems() {
        assertThrows(IllegalArgumentException.class, () -> new ShoppingCart(-1));
        assertThrows(IllegalArgumentException.class, () -> new ShoppingCart(-1, 1, List.of()));
        assertThrows(IllegalArgumentException.class, () -> new ShoppingCart(1, 1, null));
        assertThrows(IllegalArgumentException.class,
                () -> new ShoppingCart(1, 1, Collections.singletonList(null)));
        assertThrows(IllegalArgumentException.class,
                () -> new ShoppingCart(1, 1, List.of(new CartItem(10, 1), new CartItem(10, 2))));
    }

    @Test
    void rejectsInvalidOperationsWithoutChangingCart() {
        ShoppingCart cart = new ShoppingCart(1);
        cart.addBook(10);
        assertThrows(IllegalArgumentException.class, () -> cart.addBook(-1));
        assertThrows(IllegalArgumentException.class, () -> cart.removeBook(-1));
        assertThrows(IllegalArgumentException.class, () -> cart.decreaseBookQuantity(-1));
        assertEquals(List.of(new CartItem(10, 1)), cart.getCartItems());
    }

    @Test
    void copiesInitialItemsAndProtectsItsList() {
        CartItem original = new CartItem(10, 1);
        List<CartItem> items = new ArrayList<>(List.of(original));
        ShoppingCart cart = new ShoppingCart(1, 1, items);
        original.increaseQuantity();
        items.clear();
        assertEquals(1, cart.getCartItems().getFirst().getQuantity());
        assertThrows(UnsupportedOperationException.class, () -> cart.getCartItems().clear());
    }

    @Test
    void acceptsZeroUserAndBookIds() {
        ShoppingCart cart = new ShoppingCart(0);
        cart.addBook(0);
        assertEquals(0, cart.getCartItems().getFirst().getBookId());
    }
}
