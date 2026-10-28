package com.miw.domain;

import com.miw.feature.catalog.core.DomainHelper;
import com.miw.feature.catalog.core.domain.Book;
import com.miw.feature.catalog.core.domain.VAT;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BookTest {

    private Book book() {
        return new Book(1, "Title", "Author", "Description", 100, new VAT(1, 0.21, 1));
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

    @ParameterizedTest
    @MethodSource("invalidTexts")
    void rejectsInvalidTextInConstructorsAndSetters(String text) {
        VAT vat = new VAT(1, 0.21, 1);
        assertThrows(IllegalArgumentException.class, () -> new Book(text, "Author", "Description", 10, vat));
        assertThrows(IllegalArgumentException.class, () -> new Book("Title", text, "Description", 10, vat));
        assertThrows(IllegalArgumentException.class, () -> new Book("Title", "Author", text, 10, vat));

        Book book = book();
        assertThrows(IllegalArgumentException.class, () -> book.setTitle(text));
        assertThrows(IllegalArgumentException.class, () -> book.setAuthor(text));
        assertThrows(IllegalArgumentException.class, () -> book.setDescription(text));
        assertEquals("Title", book.getTitle());
        assertEquals("Author", book.getAuthor());
        assertEquals("Description", book.getDescription());
    }

    @ParameterizedTest
    @ValueSource(doubles = {-1, Double.NaN, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY})
    void rejectsInvalidBasePricesWithoutChangingPreviousValue(double price) {
        Book book = book();
        assertThrows(IllegalArgumentException.class, () -> book.setBasePrice(price));
        assertThrows(IllegalArgumentException.class, () -> new Book("T", "A", "D", price, new VAT(1, 0.21, 1)));
        assertEquals(100, book.getBasePrice());
    }

    @Test
    void acceptsTextLengthBoundariesAndFreeBooks() {
        Book book = new Book("T", "A", "D", 0, new VAT(0, 0, 0));
        book.setTitle("t".repeat(255));
        book.setAuthor("a".repeat(255));
        book.setDescription("d".repeat(255));
        assertEquals(0, book.getPrice());
        assertEquals(255, book.getTitle().length());
        assertEquals(255, book.getAuthor().length());
        assertEquals(255, book.getDescription().length());
    }

    @Test
    void rejectsNegativeIdAndMissingVat() {
        assertThrows(IllegalArgumentException.class,
                () -> new Book(-1, "T", "A", "D", 10, new VAT(1, 0.21, 1)));
        assertThrows(IllegalArgumentException.class, () -> new Book("T", "A", "D", 10, null));
        Book book = book();
        assertThrows(IllegalArgumentException.class, () -> book.setVat(null));
        assertEquals(121, book.getPrice(), 0.001);
    }

    @Test
    void priceReflectsChangesToBasePriceAndVat() {
        Book book = book();
        book.setBasePrice(50);
        book.setVat(new VAT(2, 0.04, 2));
        assertEquals(52, book.getPrice(), 0.001);
    }

    private static Stream<String> invalidTexts() {
        return Stream.of(null, "", " \t\n", "x".repeat(256));
    }
}
