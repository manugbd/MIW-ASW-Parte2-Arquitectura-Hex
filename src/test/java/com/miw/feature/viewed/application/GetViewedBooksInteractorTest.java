package com.miw.feature.viewed.application;

import com.miw.feature.viewed.core.application.GetViewedBooksInteractor;
import com.miw.feature.viewed.core.port.inbound.dto.BookDTO;
import com.miw.feature.viewed.core.port.outbound.bookcatalog.ViewedBookCatalog;
import com.miw.feature.viewed.core.port.outbound.history.ViewedBooksHistory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GetViewedBooksInteractorTest {

    private final ViewedBookCatalog catalog = mock(ViewedBookCatalog.class);
    private final ViewedBooksHistory history = mock(ViewedBooksHistory.class);
    private final GetViewedBooksInteractor interactor = new GetViewedBooksInteractor(catalog, history);

    private BookDTO book(int id) {
        return new BookDTO(id, "Book " + id, "Author", "Description", 12.1);
    }

    @Test
    void emptyHistoryDoesNotNeedTheCatalog() {
        when(history.getViewedBookIds()).thenReturn(List.of());
        assertTrue(interactor.getViewedBooks().isEmpty());
        verifyNoInteractions(catalog);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3})
    void returnsOnlyViewedBooksInHistoryOrder(int count) {
        List<Integer> ids = List.of(3, 1, 0).subList(0, count);

        when(history.getViewedBookIds()).thenReturn(ids);
        when(catalog.getBooks()).thenReturn(List.of(book(0), book(1), book(2), book(3)));
        assertEquals(ids.stream().map(this::book).toList(), interactor.getViewedBooks());
        verify(catalog).getBooks();
    }

    @Test
    void omitsDeletedBooksWithoutAddingUnvisitedOnes() {
        when(history.getViewedBookIds()).thenReturn(List.of(3, 1, 0));
        when(catalog.getBooks()).thenReturn(List.of(book(0), book(2), book(3)));
        assertEquals(List.of(book(3), book(0)), interactor.getViewedBooks());
    }

    @Test
    void handlesAnEmptyCatalog() {
        when(history.getViewedBookIds()).thenReturn(List.of(1));
        when(catalog.getBooks()).thenReturn(List.of());
        assertTrue(interactor.getViewedBooks().isEmpty());
    }

    @Test
    void usesCurrentCatalogInformationRatherThanStoringBookDetailsInHistory() {
        when(history.getViewedBookIds()).thenReturn(List.of(1));

        BookDTO updated = new BookDTO(1, "Updated title", "Author", "Description", 15);

        when(catalog.getBooks()).thenReturn(List.of(book(1)), List.of(updated));
        assertEquals(List.of(book(1)), interactor.getViewedBooks());
        assertEquals(List.of(updated), interactor.getViewedBooks());
    }
}
