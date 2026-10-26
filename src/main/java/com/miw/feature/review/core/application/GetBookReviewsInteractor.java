package com.miw.feature.review.core.application;

import com.miw.feature.review.core.port.inbound.GetBookReviews;
import com.miw.feature.review.core.port.inbound.dto.ReviewDTO;
import com.miw.feature.review.core.port.outbound.persistence.ReviewRepository;

import java.util.List;

public class GetBookReviewsInteractor implements GetBookReviews {

    // Dependencies

    private final ReviewRepository reviewRepository;

    // All args constructor

    public GetBookReviewsInteractor(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    // Business methods

    @Override
    public List<ReviewDTO> getReviewsByBook(int bookId) {
        if (bookId <= 0)
            throw new IllegalArgumentException("Book ID must be positive");

        return reviewRepository.findReviewsByBook(bookId).stream()
                .map(ReviewDTO::new).toList();
    }
}
