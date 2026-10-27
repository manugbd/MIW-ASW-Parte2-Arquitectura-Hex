package com.miw.feature.viewed.core.application;

import com.miw.feature.viewed.core.port.inbound.RecordViewedBook;
import com.miw.feature.viewed.core.port.outbound.history.ViewedBooksHistory;

public class RecordViewedBookInteractor implements RecordViewedBook {

    // Dependencies

    private final ViewedBooksHistory history;

    // All args constructor

    public RecordViewedBookInteractor(ViewedBooksHistory history) {
        this.history = history;
    }

    // Business methods

    @Override
    public void recordView(int bookId) {
        if (bookId < 0) throw new IllegalArgumentException("Book ID must not be negative");

        history.recordView(bookId);
    }
}
