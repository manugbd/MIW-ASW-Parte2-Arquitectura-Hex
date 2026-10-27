package com.miw.feature.recommendation.core.port.inbound.dto;

import com.miw.feature.review.core.domain.Review;

public record ReviewDTO(int id, int userId, String text, int score) {

    public ReviewDTO(Review review) {
        this(review.getId(),
                review.getUserId(),
                review.getText(),
                review.getScore());
    }
}
