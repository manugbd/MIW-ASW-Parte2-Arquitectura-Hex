package com.miw.feature.catalog.adapter.outbound.persistence;

import com.miw.feature.catalog.core.domain.Book;
import com.miw.feature.catalog.core.domain.VAT;
import com.miw.feature.catalog.core.port.outbound.persistence.BookRepository;
import com.miw.feature.catalog.core.port.outbound.persistence.VATRepository;

import java.util.List;
import java.util.Optional;

public class CsvVATRepositoryAdapter implements VATRepository {

    // Dependencies

    private final BookRepository bookRepository;

    // All args constructor

    public CsvVATRepositoryAdapter(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    // Business methods

    @Override
    public List<VAT> getVATs() {
        return bookRepository.getBooks().stream().map(Book::getVat).distinct().toList();
    }

    @Override
    public Optional<VAT> findVATById(int vatId) {
        return getVATs().stream().filter(vat -> vat.getId() == vatId).findFirst();
    }
}
