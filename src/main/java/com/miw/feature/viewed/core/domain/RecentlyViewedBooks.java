package com.miw.feature.viewed.core.domain;

import java.util.ArrayList;
import java.util.List;

public class RecentlyViewedBooks {

    // Constants

    private static final int MAX_BOOKS = 3;

    // Attributes

    private final List<Integer> bookIds = new ArrayList<>();

    // Empty constructor

    public RecentlyViewedBooks() {
    }

    // Getters

    public synchronized List<Integer> getBookIds() {
        return List.copyOf(bookIds);
    }

    // Business methods

    public synchronized void recordView(int bookId) {
        if (bookId < 0) throw new IllegalArgumentException("Book ID must not be negative");

        bookIds.remove(Integer.valueOf(bookId));
        bookIds.addFirst(bookId);

        if (bookIds.size() > MAX_BOOKS) bookIds.removeLast();
    }
}
