package com.miw.feature.catalog.core.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.miw.feature.catalog.core.DomainHelper;
import com.miw.feature.catalog.core.application.GetSpecialOfferBookInteractor;
import com.miw.feature.catalog.core.domain.Book;
import com.miw.feature.catalog.core.domain.VAT;
import com.miw.feature.catalog.core.port.inbound.GetSpecialOfferBook;
import com.miw.feature.catalog.core.port.inbound.dto.BookDataDTO;
import com.miw.feature.catalog.core.port.outbound.bookselector.BookSelector;
import com.miw.feature.catalog.core.port.outbound.persistence.BookRepository;

class GetSpecialOfferBookInteractorTest {

	@Test
	void getCatalogBooks() {

		// Arrange
		String[] titles = { "Title 1", "Title 2", "Title 3" };
		String[] authors = { "Author 1", "Author 2", "Author 3" };
		String[] descriptions = { "Description 1", "Description 2", "Description 3" };
		double[] basePrices = { 10.0, 20.0, 30.0 };
		double[] vatValues = { 0.21, 0.10, 0.04 };

		List<Book> books = DomainHelper.createBookList(titles, authors, descriptions, basePrices, vatValues);

		BookRepository bookRepository = mock(BookRepository.class);

		// Mock repository
		when(bookRepository.getBooks()).thenReturn(books);

		// Mook bookSelector
		BookSelector bookSelector = mock(BookSelector.class);

		VAT mockVat = DomainHelper.createVAT(1, 0.0, 1);
		Book mockSelectedBook = DomainHelper.createBook(1, "Title 1", "Author 1", "Description 1", 10.0, mockVat);

		when(bookSelector.selectBook(books)).thenReturn(mockSelectedBook);

		// Create Interactor with mocked dependencies.
		GetSpecialOfferBook interactor = new GetSpecialOfferBookInteractor(bookRepository, bookSelector);

		// Act

		BookDataDTO result = interactor.getSpecialOfferBook();

		// Assert

		assertEquals(8.5, result.price(), 0.01);

		verify(bookRepository).getBooks();
		verify(bookSelector).selectBook(books);
	}
}