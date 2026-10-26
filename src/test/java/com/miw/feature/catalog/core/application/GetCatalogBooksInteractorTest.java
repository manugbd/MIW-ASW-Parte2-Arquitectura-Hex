package com.miw.feature.catalog.core.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.miw.feature.catalog.core.DomainHelper;
import com.miw.feature.catalog.core.application.GetCatalogBooksInteractor;
import com.miw.feature.catalog.core.domain.Book;
import com.miw.feature.catalog.core.port.inbound.dto.BookDataDTO;
import com.miw.feature.catalog.core.port.outbound.persistence.BookRepository;

class GetCatalogBooksInteractorTest {

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

		// Mock
		when(bookRepository.getBooks()).thenReturn(books);

		GetCatalogBooksInteractor interactor = new GetCatalogBooksInteractor(bookRepository);

		// Act

		List<BookDataDTO> result = interactor.getCatalogBooks();

		// Assert

		assertEquals(3, result.size());

		for (int i = 0; i < result.size(); i++) {

			assertEquals(titles[i], result.get(i).title());
			assertEquals(authors[i], result.get(i).author());
			assertEquals(descriptions[i], result.get(i).description());

			double expectedPrice = basePrices[i] * (1 + vatValues[i]);

			assertEquals(expectedPrice, result.get(i).price(), 0.001);
		}

		verify(bookRepository).getBooks();
	}
}