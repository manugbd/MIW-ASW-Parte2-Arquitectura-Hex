package com.miw.feature.catalog.core.application;

import java.util.List;

import com.miw.feature.catalog.core.domain.Book;
import com.miw.feature.catalog.core.port.inbound.GetSpecialOfferBook;
import com.miw.feature.catalog.core.port.inbound.dto.BookDataDTO;
import com.miw.feature.catalog.core.port.outbound.bookselector.BookSelector;
import com.miw.feature.catalog.core.port.outbound.persistence.BookRepository;

public class GetSpecialOfferBookInteractor implements GetSpecialOfferBook {

	private final BookRepository bookRepository;
	private final BookSelector bookSelector;

	public GetSpecialOfferBookInteractor(BookRepository bookRepository, BookSelector bookSelector) {
		this.bookRepository = bookRepository;
		this.bookSelector = bookSelector;
	}

	@Override
	public BookDataDTO getSpecialOfferBook() {
		List<Book> books = bookRepository.getBooks();

		if (books.isEmpty()) {
			return null;
		}

		Book book = bookSelector.selectBook(books);

		BookDataDTO bookDataDTO = new BookDataDTO(book.getId(), book.getTitle(), book.getAuthor(), book.getDescription(),
				book.getDiscountedPrice(0.85));

		return bookDataDTO;
	}

}
