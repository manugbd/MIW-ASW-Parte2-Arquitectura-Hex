package com.miw.feature.shoppingcart.core.port.outbound.event;

import com.miw.event.Event;

public record BookAddedToCartEvent(int userId, int bookId) implements Event {

}