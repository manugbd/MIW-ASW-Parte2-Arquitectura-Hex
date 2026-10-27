package com.miw.feature.recommendation.configuration;

import com.miw.feature.recommendation.adapter.outbound.reviews.GetBookReviewsOfBookAdapter;
import com.miw.feature.recommendation.core.application.GetRecommendedReviewInteractor;
import com.miw.feature.recommendation.core.port.inbound.GetRecommendedReview;
import com.miw.feature.recommendation.core.port.outbound.review.GetBookReviewsOfBook;
import com.miw.feature.review.core.port.inbound.GetBookReviews;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RecommendationConfiguration {

    @Bean
    public GetRecommendedReview getRecommendedReview(GetBookReviewsOfBook reviewSelector) {
        return new GetRecommendedReviewInteractor(reviewSelector);
    }

    @Bean
    public GetBookReviewsOfBook getBookReviews(GetBookReviews reviewSelector) {
        return new GetBookReviewsOfBookAdapter(reviewSelector);
    }
}
