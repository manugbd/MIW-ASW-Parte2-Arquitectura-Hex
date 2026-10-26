package com.miw.feature.review.core.port.inbound;

import com.miw.feature.review.core.port.inbound.dto.ReviewDTO;

public interface AddBookReview {

    ReviewDTO addReview(int userId, int bookId, String text, int score);
}
