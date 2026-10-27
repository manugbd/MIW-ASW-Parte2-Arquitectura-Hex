package com.miw.feature.recommendation.core.port.outbound.review;

import java.util.List;
import com.miw.feature.recommendation.core.port.inbound.dto.ReviewDTO;

public interface GetBookReviewsOfBook {

    List<ReviewDTO> getReviwsByBook(int bookId);
}
