package com.miw.feature.catalog.core.application;

import com.miw.feature.catalog.core.domain.Book;
import com.miw.feature.catalog.core.domain.VAT;
import com.miw.feature.catalog.core.port.inbound.AddBook;
import com.miw.feature.catalog.core.port.inbound.dto.BookDataDTO;
import com.miw.feature.catalog.core.port.inbound.exception.VATNotFoundException;
import com.miw.feature.catalog.core.port.outbound.persistence.BookRepository;
import com.miw.feature.catalog.core.port.outbound.persistence.VATRepository;

public class AddBookInteractor implements AddBook {

    // Dependencies

    private final BookRepository bookRepository;
    private final VATRepository vatRepository;

    // All args constructor

    public AddBookInteractor(BookRepository bookRepository, VATRepository vatRepository) {
        this.bookRepository = bookRepository;
        this.vatRepository = vatRepository;
    }

    // Business methods

    @Override
    public BookDataDTO addBook(String title, String author, String description, double basePrice, int vatId) {
        if (vatId < 0) throw new IllegalArgumentException("VAT ID must not be negative");

        VAT vat = vatRepository.findVATById(vatId)
                .orElseThrow(() -> new VATNotFoundException(vatId));
        Book book = bookRepository.add(
                new Book(title,
                        author,
                        description,
                        basePrice,
                        vat
                ));

        return new BookDataDTO(book.getId(), book.getTitle(), book.getAuthor(), book.getDescription(), book.getPrice());
    }
}
