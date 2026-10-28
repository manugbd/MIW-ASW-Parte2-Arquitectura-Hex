package com.miw.feature.recommendation.selector;

import com.miw.feature.recommendation.adapter.outbound.selector.FirstReviewSelector;
import com.miw.feature.recommendation.core.port.inbound.dto.ReviewDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FirstReviewSelectorTest {
    private final FirstReviewSelector selector = new FirstReviewSelector();

    @Test
    void selectsFirstAvailableRegardlessOfIdOrScore() {
        ReviewDTO first = new ReviewDTO(9, 2, "First available", 0);
        ReviewDTO other = new ReviewDTO(0, 3, "Higher score and lower ID", 10);

        assertSame(first, selector.selectReview(List.of(first, other)).orElseThrow());
        assertSame(other, selector.selectReview(List.of(other, first)).orElseThrow());
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
    void doesNotModifyTheInputList() {
        List<ReviewDTO> reviews = new ArrayList<>(List.of(
                new ReviewDTO(7, 2, "First", 2), new ReviewDTO(1, 3, "Second", 10)));
        List<ReviewDTO> original = List.copyOf(reviews);

        selector.selectReview(reviews);
        assertEquals(original, reviews);
    }
}
