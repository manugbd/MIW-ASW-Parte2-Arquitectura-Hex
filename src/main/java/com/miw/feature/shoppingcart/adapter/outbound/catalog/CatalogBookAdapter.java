package com.miw.feature.shoppingcart.adapter.outbound.catalog;

import java.util.Optional;

import com.miw.feature.catalog.core.port.inbound.FindBook;
import com.miw.feature.shoppingcart.core.port.outbound.catalog.BookCatalog;
import com.miw.feature.shoppingcart.core.port.outbound.catalog.BookForCartDTO;

public class CatalogBookAdapter implements BookCatalog {

	private final FindBook findBook;

	public CatalogBookAdapter(FindBook findBook) {
		this.findBook = findBook;
	}

	@Override
	public Optional<BookForCartDTO> findBookById(int bookId) {
		return findBook.findBook(bookId)
				.map(book -> new BookForCartDTO(
						book.id(),
						book.title(),
						book.price()));
	}
}
