package com.miw.feature.catalog.core.application;

import com.miw.feature.catalog.core.DomainHelper;
import com.miw.feature.catalog.core.domain.Book;
import com.miw.feature.catalog.core.domain.VAT;
import com.miw.feature.catalog.core.port.inbound.dto.BookDataDTO;
import com.miw.feature.catalog.core.port.outbound.persistence.BookRepository;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class GetCatalogBooksInteractorTest {
    private final BookRepository repository = mock(BookRepository.class);
    private final GetCatalogBooksInteractor interactor = new GetCatalogBooksInteractor(repository);

    @Test
    void getCatalogBooks() {

        // Arrange
        String[] titles = {"Title 1", "Title 2", "Title 3"};
        String[] authors = {"Author 1", "Author 2", "Author 3"};
        String[] descriptions = {"Description 1", "Description 2", "Description 3"};
        double[] basePrices = {10.0, 20.0, 30.0};
        double[] vatValues = {0.21, 0.10, 0.04};

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

    @Test
    void returnsAllBooksWithTheirDataAndPriceIncludingVat() {
        when(repository.getBooks()).thenReturn(List.of(
                new Book(2, "Dune", "Frank Herbert", "First description", 100, new VAT(1, 0.21, 1)),
                new Book(1, "Foundation", "Isaac Asimov", "Second description", 50, new VAT(2, 0.04, 2))));
        List<BookDataDTO> result = interactor.getCatalogBooks();
        assertEquals(2, result.size());
        assertEquals(2, result.getFirst().id());
        assertEquals("Dune", result.getFirst().title());
        assertEquals("Frank Herbert", result.getFirst().author());
        assertEquals("First description", result.getFirst().description());
        assertEquals(121, result.getFirst().price(), 0.001);
        assertEquals(1, result.getLast().id());
        assertEquals("Foundation", result.getLast().title());
        assertEquals(52, result.getLast().price(), 0.001);
    }

    @Test
    void returnsEmptyListWhenCatalogIsEmpty() {
        when(repository.getBooks()).thenReturn(List.of());
        assertTrue(interactor.getCatalogBooks().isEmpty());
    }
}
