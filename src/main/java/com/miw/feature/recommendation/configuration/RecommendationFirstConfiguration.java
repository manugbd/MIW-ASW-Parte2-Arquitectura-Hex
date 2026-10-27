package com.miw.feature.recommendation.configuration;

import com.miw.feature.recommendation.adapter.outbound.selector.FirstReviewSelector;
import com.miw.feature.recommendation.core.port.outbound.selector.ReviewSelector;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("first")
public class RecommendationFirstConfiguration {

    @Bean
    public ReviewSelector firstReviewSelector() {
        return new FirstReviewSelector();
    }

}
