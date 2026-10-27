package com.miw.feature.viewed.adapter.outbound.history;

import com.miw.feature.viewed.core.domain.RecentlyViewedBooks;
import com.miw.feature.viewed.core.port.outbound.history.ViewedBooksHistory;

import java.util.List;
import java.util.Objects;

public class SessionViewedBooksHistoryAdapter implements ViewedBooksHistory {

    // Attributes

    private final RecentlyViewedBooks history;

    // Empty constructor

    public SessionViewedBooksHistoryAdapter(RecentlyViewedBooks history) {
        this.history = Objects.requireNonNull(history, "History is required");
    }

    // Business methods

    @Override
    public void recordView(int bookId) {
        history.recordView(bookId);
    }

    @Override
    public List<Integer> getViewedBookIds() {
        return history.getBookIds();
    }
}
