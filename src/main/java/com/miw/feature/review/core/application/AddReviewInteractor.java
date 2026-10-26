package com.miw.feature.review.core.application;

import com.miw.feature.review.core.domain.Review;
import com.miw.feature.review.core.port.inbound.AddBookReview;
import com.miw.feature.review.core.port.inbound.dto.ReviewDTO;
import com.miw.feature.review.core.port.inbound.exception.BookNotFoundException;
import com.miw.feature.review.core.port.inbound.exception.DuplicateReviewException;
import com.miw.feature.review.core.port.outbound.catalog.BookCatalog;
import com.miw.feature.review.core.port.outbound.persistence.ReviewRepository;

public class AddReviewInteractor implements AddBookReview {

    // Attributes

    private final ReviewRepository reviewRepository;
    private final BookCatalog bookCatalog;

    // All args constructor

    public AddReviewInteractor(ReviewRepository reviewRepository, BookCatalog bookCatalog) {
        this.reviewRepository = reviewRepository;
        this.bookCatalog = bookCatalog;
    }

    // Business methods

    @Override
    public ReviewDTO addReview(int userId, int bookId, String text, int score) {
        if (userId < 0 || bookId < 0)
            throw new IllegalArgumentException("User and book IDs must be positive");

        bookCatalog.findBookById(bookId).orElseThrow(() -> new BookNotFoundException(bookId));

        if (reviewRepository.existsByBookIdAndUserId(bookId, userId))
            throw new DuplicateReviewException();

        Review review = new Review(
                bookId,
                userId,
                text,
                score
        );

        Review savedReview = reviewRepository.save(review);
        return new ReviewDTO(savedReview);
    }
}
