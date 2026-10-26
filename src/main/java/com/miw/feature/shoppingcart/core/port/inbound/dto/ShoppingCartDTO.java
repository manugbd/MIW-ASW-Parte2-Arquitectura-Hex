package com.miw.feature.shoppingcart.core.port.inbound.dto;

import java.util.List;

public record ShoppingCartDTO(List<CartItemDTO> items, double totalPrice) {

}
