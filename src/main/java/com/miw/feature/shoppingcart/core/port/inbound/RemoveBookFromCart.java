package com.miw.feature.shoppingcart.core.port.inbound;

public interface RemoveBookFromCart {

	void removeBook(int userId, int bookId);
}
