package com.miw.feature.catalog.core.application;

import java.util.List;

import com.miw.feature.catalog.core.domain.Book;
import com.miw.feature.catalog.core.port.inbound.GetCatalogBooks;
import com.miw.feature.catalog.core.port.inbound.dto.BookDataDTO;
import com.miw.feature.catalog.core.port.outbound.persistence.BookRepository;

public class GetCatalogBooksInteractor implements GetCatalogBooks {

	private final BookRepository bookRepository;

	public GetCatalogBooksInteractor(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}

	@Override
	public List<BookDataDTO> getCatalogBooks() {
		List<Book> books = bookRepository.getBooks();

		List<BookDataDTO> bookDataList = books.stream()
				.map(book -> new BookDataDTO(book.getId(), book.getTitle(), book.getAuthor(), book.getDescription(), book.getPrice()))
				.toList();

		return bookDataList;
	}

}
