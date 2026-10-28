package com.miw.feature.recommendation.application;

import com.miw.feature.recommendation.core.application.GetRecommendedReviewInteractor;
import com.miw.feature.recommendation.core.port.inbound.GetRecommendedReview;
import com.miw.feature.recommendation.core.port.inbound.dto.ReviewDTO;
import com.miw.feature.recommendation.core.port.outbound.review.GetBookReviewsOfBook;
import com.miw.feature.recommendation.core.port.outbound.selector.ReviewSelector;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GetRecommendedReviewInteractorTest {

    private final GetBookReviewsOfBook reviews = mock(GetBookReviewsOfBook.class);
    private final ReviewSelector selector = mock(ReviewSelector.class);
    private final GetRecommendedReviewInteractor interactor = new GetRecommendedReviewInteractor(reviews, selector);

    @Test
    void obtainsBookReviewsAndReturnsTheInjectedSelectorsChoice() {
        ReviewDTO first = new ReviewDTO(1, 2, "First", 10);
        ReviewDTO selected = new ReviewDTO(2, 3, "Selected", 3);

        List<ReviewDTO> candidates = List.of(first, selected);

        when(reviews.getReviwsByBook(4)).thenReturn(candidates);
        when(selector.selectReview(candidates)).thenReturn(Optional.of(selected));
        assertSame(selected, interactor.getRecommendedReview(4).orElseThrow());
        verify(reviews).getReviwsByBook(4);
        verify(selector).selectReview(candidates);
    }

    @Test
    void bookWithoutReviewsHasNoFeaturedReview() {
        when(reviews.getReviwsByBook(4)).thenReturn(List.of());
        when(selector.selectReview(List.of())).thenReturn(Optional.empty());
        assertTrue(interactor.getRecommendedReview(4).isEmpty());
    }

    @Test
    void rejectsMissingReviewListBeforeSelecting() {
        when(reviews.getReviwsByBook(4)).thenReturn(null);
        assertThrows(IllegalArgumentException.class, () -> interactor.getRecommendedReview(4));
        verifyNoInteractions(selector);
    }

    @Test
    void rejectsNullReviewElementsBeforeSelecting() {
        when(reviews.getReviwsByBook(4)).thenReturn(Arrays.asList(new ReviewDTO(1, 2, "Text", 5), null));
        assertThrows(IllegalArgumentException.class, () -> interactor.getRecommendedReview(4));
        verifyNoInteractions(selector);
    }

    @Test
    void anotherCriterionCanBeInjectedWithoutChangingTheUseCase() {
        ReviewDTO first = new ReviewDTO(1, 2, "First", 10);
        ReviewDTO last = new ReviewDTO(2, 3, "Last", 3);

        when(reviews.getReviwsByBook(4)).thenReturn(List.of(first, last));

        ReviewSelector lastAvailable = candidates -> candidates.stream().reduce((previous, current) -> current);

        GetRecommendedReview anotherInteractor = new GetRecommendedReviewInteractor(reviews, lastAvailable);

        assertSame(last, anotherInteractor.getRecommendedReview(4).orElseThrow());
    }
}
