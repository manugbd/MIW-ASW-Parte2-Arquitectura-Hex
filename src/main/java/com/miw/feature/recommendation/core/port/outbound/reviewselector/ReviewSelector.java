package com.miw.feature.recommendation.core.port.outbound.reviewselector;

import java.util.List;
import java.util.Optional;
import com.miw.feature.review.core.port.inbound.dto.ReviewDTO;

public interface ReviewSelector {

    Optional<ReviewDTO> selectReview(List<ReviewDTO> reviews);
}
