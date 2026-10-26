package com.miw.feature.review.core.port.inbound.exception;

public class BookNotFoundException extends RuntimeException {

    public BookNotFoundException(int bookId) {
        super("Book " + bookId + " does not exist");
    }
}
