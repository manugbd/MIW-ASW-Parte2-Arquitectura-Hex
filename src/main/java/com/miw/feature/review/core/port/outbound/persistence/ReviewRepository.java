package com.miw.feature.review.core.port.outbound.persistence;

import java.util.List;
import java.util.Optional;
import com.miw.feature.review.core.domain.Review;

public interface ReviewRepository {

    List<Review> findReviewsByBook(int bookId);

    boolean existsByBookIdAndUserId(int bookId, int userId);

    Review save(Review review);
}
