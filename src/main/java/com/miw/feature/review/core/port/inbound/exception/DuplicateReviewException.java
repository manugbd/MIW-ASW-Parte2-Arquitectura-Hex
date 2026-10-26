package com.miw.feature.review.core.port.inbound.exception;

public class DuplicateReviewException extends RuntimeException {

    public DuplicateReviewException() {
        super("The user has already reviewed this book");
    }

    public DuplicateReviewException(Throwable cause) {
        super("The user has already reviewed this book", cause);
    }
}
