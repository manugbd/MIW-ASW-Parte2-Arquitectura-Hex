package com.miw.feature.recommendation.selector;

import com.miw.feature.recommendation.adapter.outbound.selector.BestScoreReviewSelector;
import com.miw.feature.recommendation.core.port.inbound.dto.ReviewDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BestScoreReviewSelectorTest {

    private final BestScoreReviewSelector selector = new BestScoreReviewSelector();

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2})
    void selectsHighestScoreInAnyPosition(int position) {
        ReviewDTO best = new ReviewDTO(9, 3, "Highest score", 10);

        List<ReviewDTO> reviews = new ArrayList<>(List.of(new ReviewDTO(0, 2, "Low score", 0), new ReviewDTO(4, 4, "Middle score", 6)));
        reviews.add(position, best);

        assertSame(best, selector.selectReview(reviews).orElseThrow());
    }

    @Test
    void keepsFirstAvailableWhenHighestScoresTie() {
        ReviewDTO first = new ReviewDTO(9, 2, "First available", 8);
        ReviewDTO second = new ReviewDTO(0, 3, "Second available", 8);
        ReviewDTO lower = new ReviewDTO(4, 4, "Lower", 2);

        assertSame(first, selector.selectReview(List.of(lower, first, second)).orElseThrow());
        assertSame(second, selector.selectReview(List.of(second, first, lower)).orElseThrow());
    }

    @Test
    void returnsEmptyWithoutReviews() {
        assertTrue(selector.selectReview(List.of()).isEmpty());
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 5, 10})
    void selectsTheOnlyReviewWhateverItsScore(int score) {
        ReviewDTO review = new ReviewDTO(0, 2, "Only review", score);
        assertSame(review, selector.selectReview(List.of(review)).orElseThrow());
    }

    @Test
    void selectsAReviewWhenAllScoresAreZero() {
        ReviewDTO first = new ReviewDTO(9, 2, "First zero", 0);
        assertSame(first, selector.selectReview(List.of(first, new ReviewDTO(0, 3, "Second zero", 0))).orElseThrow());
    }

    @Test
    void doesNotModifyTheInputList() {
        List<ReviewDTO> reviews = new ArrayList<>(List.of(new ReviewDTO(7, 2, "First", 2), new ReviewDTO(1, 3, "Second", 10)));

        List<ReviewDTO> original = List.copyOf(reviews);
        selector.selectReview(reviews);
        assertEquals(original, reviews);
    }
}
