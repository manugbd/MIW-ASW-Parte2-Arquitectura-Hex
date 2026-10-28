package com.miw.feature.catalog.core.application;

import com.miw.feature.catalog.core.domain.Book;
import com.miw.feature.catalog.core.domain.VAT;
import com.miw.feature.catalog.core.port.inbound.dto.BookDataDTO;
import com.miw.feature.catalog.core.port.inbound.exception.VATNotFoundException;
import com.miw.feature.catalog.core.port.outbound.persistence.BookRepository;
import com.miw.feature.catalog.core.port.outbound.persistence.VATRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AddBookInteractorTest {

    private final BookRepository books = mock(BookRepository.class);
    private final VATRepository vats = mock(VATRepository.class);
    private final AddBookInteractor interactor = new AddBookInteractor(books, vats);

    @Test
    void savesNewBookUsingExistingVatAndReturnsGeneratedId() {
        VAT vat = new VAT(2, 0.21, 7);
        when(vats.findVATById(2)).thenReturn(Optional.of(vat));
        when(books.add(any(Book.class))).thenReturn(new Book(8, "Title", "Author", "Description", 100, vat));

        BookDataDTO result = interactor.addBook("Title", "Author", "Description", 100, 2);

        ArgumentCaptor<Book> captured = ArgumentCaptor.forClass(Book.class);
        verify(books).add(captured.capture());
        Book saved = captured.getValue();

        assertEquals(0, saved.getId());
        assertEquals("Title", saved.getTitle());
        assertEquals("Author", saved.getAuthor());
        assertEquals("Description", saved.getDescription());
        assertEquals(100, saved.getBasePrice());
        assertSame(vat, saved.getVat());
        assertEquals(8, result.id());
        assertEquals("Title", result.title());
        assertEquals("Author", result.author());
        assertEquals("Description", result.description());
        assertEquals(121, result.price(), 0.001);
    }

    @Test
    void missingVatPreventsCreation() {
        when(vats.findVATById(99)).thenReturn(Optional.empty());
        assertThrows(VATNotFoundException.class, () -> interactor.addBook("T", "A", "D", 10, 99));
        verifyNoInteractions(books);
    }

    @Test
    void negativeVatIdIsRejectedBeforeCallingRepositories() {
        assertThrows(IllegalArgumentException.class, () -> interactor.addBook("T", "A", "D", 10, -1));
        verifyNoInteractions(books, vats);
    }

    @Test
    void invalidBookDataIsNotSaved() {
        when(vats.findVATById(1)).thenReturn(Optional.of(new VAT(1, 0.21, 1)));
        assertThrows(IllegalArgumentException.class, () -> interactor.addBook("", "A", "D", 10, 1));
        verifyNoInteractions(books);
    }
}
