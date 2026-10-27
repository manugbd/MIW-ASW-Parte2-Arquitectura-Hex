package com.miw.feature.recommendation.core.port.outbound.selector;

import com.miw.feature.recommendation.core.port.inbound.dto.ReviewDTO;

import java.util.List;
import java.util.Optional;

public interface ReviewSelector {

    Optional<ReviewDTO> selectReview(List<ReviewDTO> reviews);
}
