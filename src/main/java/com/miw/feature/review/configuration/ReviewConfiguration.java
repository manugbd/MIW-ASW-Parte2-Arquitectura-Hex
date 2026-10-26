package com.miw.feature.review.configuration;

import com.miw.feature.catalog.core.port.inbound.FindBook;
import com.miw.feature.review.adapter.outbound.catalog.CatalogBookAdapter;
import com.miw.feature.review.core.application.AddReviewInteractor;
import com.miw.feature.review.core.application.GetBookReviewsInteractor;
import com.miw.feature.review.core.port.inbound.AddBookReview;
import com.miw.feature.review.core.port.inbound.GetBookReviews;
import com.miw.feature.review.core.port.outbound.catalog.BookCatalog;
import com.miw.feature.review.core.port.outbound.persistence.ReviewRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ReviewConfiguration {

    @Bean
    public AddBookReview addReview(ReviewRepository reviewRepository, BookCatalog bookCatalog) {
        return new AddReviewInteractor(reviewRepository, bookCatalog);
    }

    @Bean
    public GetBookReviews getBookReviews(ReviewRepository reviewRepository) {
        return new GetBookReviewsInteractor(reviewRepository);
    }

    @Bean
    public BookCatalog bookCatalog(FindBook findBook) {
        return new CatalogBookAdapter(findBook);
    }

}
