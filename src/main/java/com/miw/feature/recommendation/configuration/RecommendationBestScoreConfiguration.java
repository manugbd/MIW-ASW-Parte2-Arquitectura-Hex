package com.miw.feature.recommendation.configuration;

import com.miw.feature.recommendation.adapter.outbound.selector.BestScoreReviewSelector;
import com.miw.feature.recommendation.core.port.outbound.selector.ReviewSelector;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("bestScore")
public class RecommendationBestScoreConfiguration {

    @Bean
    public ReviewSelector bestScoreReviewSelector() {
        return new BestScoreReviewSelector();
    }

}
