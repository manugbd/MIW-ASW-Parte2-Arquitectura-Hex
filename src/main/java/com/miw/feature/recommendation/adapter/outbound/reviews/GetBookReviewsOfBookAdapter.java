package com.miw.feature.recommendation.adapter.outbound.reviews;

import com.miw.feature.recommendation.core.port.inbound.dto.ReviewDTO;
import com.miw.feature.recommendation.core.port.outbound.review.GetBookReviewsOfBook;
import com.miw.feature.review.core.port.inbound.GetBookReviews;

import java.util.List;

public class GetBookReviewsOfBookAdapter implements GetBookReviewsOfBook {

    private final GetBookReviews getBookReviews;

    public GetBookReviewsOfBookAdapter(GetBookReviews getBookReviews) {
        this.getBookReviews = getBookReviews;
    }

    @Override
    public List<ReviewDTO> getReviwsByBook(int bookId) {
        return getBookReviews.getReviewsByBook(bookId)
                .stream().map(review ->
                        new ReviewDTO(
                                review.id(),
                                review.userId(),
                                review.text(),
                                review.score()
                        )
                ).toList();
    }
}
