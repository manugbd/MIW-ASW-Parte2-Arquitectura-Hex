package com.miw.feature.shoppingcart.core.port.inbound.dto;

public record CartItemDTO(
		int bookId,
		String title,
		double unitPrice,
		int quantity,
		double subtotal) {

}
