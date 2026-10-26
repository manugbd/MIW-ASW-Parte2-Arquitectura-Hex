package com.miw.feature.review.configuration;

import com.miw.feature.review.adapter.outbound.persistence.JpaReviewRepositoryAdapter;
import com.miw.feature.review.core.port.outbound.persistence.ReviewRepository;
import jakarta.persistence.EntityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("jpa")
public class ReviewJpaConfiguration {

    @Bean
    public ReviewRepository reviewRepository(EntityManager entityManager) {
        return new JpaReviewRepositoryAdapter(entityManager);
    }
}
