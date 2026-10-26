package com.miw.feature.shoppingcart.core.port.inbound;

import com.miw.feature.shoppingcart.core.port.inbound.dto.ShoppingCartDTO;

public interface GetShoppingCart {

	ShoppingCartDTO getShoppingCart(int userId);
}
