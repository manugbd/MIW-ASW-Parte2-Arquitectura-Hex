package com.miw.domain;

import com.miw.feature.shoppingcart.core.domain.CartItem;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CartItemTest {

    @Test
    void increasesAndDecreasesQuantity() {
        CartItem item = new CartItem(0, 1);
        item.increaseQuantity();
        assertEquals(2, item.getQuantity());
        item.decreaseQuantity();
        assertEquals(1, item.getQuantity());
    }

    @Test
    void cannotDecreaseBelowOne() {
        CartItem item = new CartItem(1, 1);
        assertThrows(IllegalStateException.class, item::decreaseQuantity);
        assertEquals(1, item.getQuantity());
    }

    @ParameterizedTest
    @ValueSource(ints = {0, -1})
    void rejectsNonPositiveQuantity(int quantity) {
        assertThrows(IllegalArgumentException.class, () -> new CartItem(1, quantity));
    }

    @Test
    void rejectsNegativeBookId() {
        assertThrows(IllegalArgumentException.class, () -> new CartItem(-1, 1));
    }
}
