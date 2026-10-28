package com.miw.feature.catalog.core.application;

import com.miw.feature.catalog.core.domain.Book;
import com.miw.feature.catalog.core.domain.VAT;
import com.miw.feature.catalog.core.port.inbound.dto.BookDataDTO;
import com.miw.feature.catalog.core.port.outbound.bookselector.BookSelector;
import com.miw.feature.catalog.core.port.outbound.persistence.BookRepository;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

class GetSpecialOfferBookInteractorTest {
    private final BookRepository repository = mock(BookRepository.class);
    private final BookSelector selector = mock(BookSelector.class);
    private final GetSpecialOfferBookInteractor interactor = new GetSpecialOfferBookInteractor(repository, selector);

    @Test
    void discountsTheSelectedBookByFifteenPercentIncludingVat() {
        Book first = new Book(1, "First", "Author", "Description", 10, new VAT(1, 0, 1));
        Book selected = new Book(2, "Selected", "Other author", "Other description", 100, new VAT(2, 0.21, 2));

        List<Book> books = List.of(first, selected);

        when(repository.getBooks()).thenReturn(books);
        when(selector.selectBook(books)).thenReturn(selected);

        BookDataDTO result = interactor.getSpecialOfferBook();

        assertEquals(2, result.id());
        assertEquals("Selected", result.title());
        assertEquals("Other author", result.author());
        assertEquals("Other description", result.description());
        assertEquals(102.85, result.price(), 0.001);
        verify(selector).selectBook(books);
    }

    @Test
    void emptyCatalogHasNoSpecialOffer() {
        when(repository.getBooks()).thenReturn(List.of());
        assertNull(interactor.getSpecialOfferBook());
        verifyNoInteractions(selector);
    }
}
