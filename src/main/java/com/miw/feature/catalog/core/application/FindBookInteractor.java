package com.miw.feature.catalog.core.application;

import java.util.Optional;

import com.miw.feature.catalog.core.port.inbound.FindBook;
import com.miw.feature.catalog.core.port.inbound.dto.BookDataDTO;
import com.miw.feature.catalog.core.port.outbound.persistence.BookRepository;

public class FindBookInteractor implements FindBook {

	private final BookRepository bookRepository;

	public FindBookInteractor(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}

	@Override
	public Optional<BookDataDTO> findBook(int bookId) {
		return bookRepository.findBookById(bookId)
				.map(book -> new BookDataDTO(
						book.getId(),
						book.getTitle(),
						book.getAuthor(),
						book.getDescription(),
						book.getPrice()));
	}
}
