package com.miw.feature.viewed.core.application;

import com.miw.feature.viewed.core.port.inbound.GetViewedBooks;
import com.miw.feature.viewed.core.port.inbound.dto.BookDTO;
import com.miw.feature.viewed.core.port.outbound.bookcatalog.ViewedBookCatalog;
import com.miw.feature.viewed.core.port.outbound.history.ViewedBooksHistory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class GetViewedBooksInteractor implements GetViewedBooks {

    // Dependencies

    private final ViewedBookCatalog bookCatalog;
    private final ViewedBooksHistory history;

    // All args constructor

    public GetViewedBooksInteractor(ViewedBookCatalog bookCatalog, ViewedBooksHistory history) {
        this.bookCatalog = bookCatalog;
        this.history = history;
    }

    @Override
    public List<BookDTO> getViewedBooks() {

        List<Integer> bookIds = history.getViewedBookIds();
        if (bookIds.isEmpty()) return List.of();

        Map<Integer, BookDTO> booksById = new HashMap<>();
        for (BookDTO book : bookCatalog.getBooks())
            booksById.put(book.id(), book);

        return bookIds.stream()
                .map(booksById::get)
                .filter(Objects::nonNull)
                .toList();
    }

}
