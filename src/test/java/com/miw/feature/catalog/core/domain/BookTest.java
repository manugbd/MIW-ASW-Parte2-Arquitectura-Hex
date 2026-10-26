package com.miw.feature.catalog.core.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.miw.feature.catalog.core.DomainHelper;
import com.miw.feature.catalog.core.domain.Book;
import com.miw.feature.catalog.core.domain.VAT;

class BookTest {

	@Test
	void calculatePriceIncludingVat() {

		// Arrange
		VAT vat = DomainHelper.createVAT(1, 0.21, 1);
		Book book = DomainHelper.createBook(1, "Book 1", "Author 1", "Description 1", 100.0, vat);

		// Act
		double price = book.getPrice();

		// Assert
		assertEquals(121.0, price, 0.001);
	}

	@Test
	void calculateDiscountedPrice() {

		// Arrange
		VAT vat = DomainHelper.createVAT(1, 0.21, 1);
		Book book = DomainHelper.createBook(1, "Book 1", "Author 1", "Description 1", 100.0, vat);

		// Act
		double priceDiscounted = book.getDiscountedPrice(0.85);
		double priceNoDiscount = book.getDiscountedPrice(1.0);
		double priceFree = book.getDiscountedPrice(0.0);

		// Assert
		assertEquals(102.85, priceDiscounted, 0.001);
		assertEquals(121.0, priceNoDiscount, 0.001);
		assertEquals(0.0, priceFree, 0.001);
	}
}