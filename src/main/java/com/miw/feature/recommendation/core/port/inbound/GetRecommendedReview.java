package com.miw.feature.recommendation.core.port.inbound;

import com.miw.feature.recommendation.core.port.inbound.dto.ReviewDTO;

import java.util.Optional;

public interface GetRecommendedReview {

    Optional<ReviewDTO> getRecommendedReview();
}
