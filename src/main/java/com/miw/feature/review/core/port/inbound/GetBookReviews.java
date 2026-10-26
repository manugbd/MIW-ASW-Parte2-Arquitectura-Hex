package com.miw.feature.review.core.port.inbound;

import com.miw.feature.review.core.port.inbound.dto.ReviewDTO;

import java.util.List;

public interface GetBookReviews {

    List<ReviewDTO> getReviewsByBook(int bookId);
}
