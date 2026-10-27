package com.miw.feature.recommendation.adapter.outbound.selector;

import com.miw.feature.recommendation.core.port.inbound.dto.ReviewDTO;
import com.miw.feature.recommendation.core.port.outbound.selector.ReviewSelector;

import java.util.List;
import java.util.Optional;

public class BestScoreReviewSelector implements ReviewSelector {

    @Override
    public Optional<ReviewDTO> selectReview(List<ReviewDTO> reviews) {
        return Optional.empty();
    }

}
