package com.miw.feature.review.adapter.outbound.catalog;

import com.miw.feature.catalog.core.port.inbound.FindBook;
import com.miw.feature.review.core.port.outbound.catalog.BookCatalog;
import com.miw.feature.review.core.port.outbound.catalog.BookForCartDTO;

import java.util.Optional;

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
						book.title()
				));
	}
}
