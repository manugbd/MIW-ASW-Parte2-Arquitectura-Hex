package com.miw.feature.catalog.core.domain;

import com.miw.feature.catalog.core.DomainHelper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BookTest {

	@Test
	void rejectsInvalidBookData() {
		Book book = new Book();
		assertThrows(IllegalArgumentException.class, () -> book.setTitle(" "));
		assertThrows(IllegalArgumentException.class, () -> book.setAuthor(null));
		assertThrows(IllegalArgumentException.class, () -> book.setDescription(""));
		assertThrows(IllegalArgumentException.class, () -> book.setVat(null));
		assertThrows(IllegalArgumentException.class, () -> book.setBasePrice(-1));
		assertThrows(IllegalArgumentException.class, () -> book.setBasePrice(Double.NaN));
		assertThrows(IllegalArgumentException.class, () -> book.setBasePrice(Double.POSITIVE_INFINITY));
	}

	@Test
	void rejectsInvalidVatAndPriceMultipliers() {
		VAT vat = DomainHelper.createVAT(1, 0.21, 1);
		Book book = DomainHelper.createBook(1, "Title", "Author", "Description", 100, vat);
		assertThrows(IllegalArgumentException.class, () -> vat.setValue(-0.21));
		assertThrows(IllegalArgumentException.class, () -> vat.setValue(Double.NaN));
		assertThrows(IllegalArgumentException.class, () -> book.getDiscountedPrice(-0.1));
		assertThrows(IllegalArgumentException.class, () -> book.getDiscountedPrice(1.1));
		assertThrows(IllegalArgumentException.class, () -> book.getDiscountedPrice(Double.NaN));
	}

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
