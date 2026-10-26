package com.miw.feature.shoppingcart.core.port.inbound;

public interface DecreaseBookQuantity {

	void decreaseBookQuantity(int userId, int bookId);
}
