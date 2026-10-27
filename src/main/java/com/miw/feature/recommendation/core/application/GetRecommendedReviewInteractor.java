package com.miw.feature.recommendation.core.application;

import com.miw.feature.recommendation.core.port.inbound.GetRecommendedReview;
import com.miw.feature.recommendation.core.port.inbound.dto.ReviewDTO;
import com.miw.feature.recommendation.core.port.outbound.review.GetBookReviewsOfBook;
import com.miw.feature.recommendation.core.port.outbound.selector.ReviewSelector;

import java.util.List;
import java.util.Optional;

public class GetRecommendedReviewInteractor implements GetRecommendedReview {

    // Dependencies

    private final GetBookReviewsOfBook getBookReviews;
    private final ReviewSelector reviewSelector;

    // All args constructor

    public GetRecommendedReviewInteractor(
            GetBookReviewsOfBook getBookReviews,
            ReviewSelector reviewSelector
    ) {
        this.getBookReviews = getBookReviews;
        this.reviewSelector = reviewSelector;
    }

    // Business methods

    @Override
    public Optional<ReviewDTO> getRecommendedReview(int bookId) {
        List<ReviewDTO> reviews = getBookReviews.getReviwsByBook(bookId);

        if (reviews == null || reviews.stream().anyMatch(review -> review == null)) {
            throw new IllegalArgumentException("Reviews are required and must not contain null elements");
        }

        return reviewSelector.selectReview(List.copyOf(reviews));
    }
}
