package com.miw.domain;

import com.miw.feature.review.core.domain.Review;

import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class ReviewTest {

    @ParameterizedTest
    @ValueSource(ints = {0, 5, 10})
    void acceptsScoresWithinTheAllowedRange(int score) {
        Review review = new Review(1, 2, "A useful review", score);

        assertEquals(score, review.getScore());
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 11, Integer.MIN_VALUE, Integer.MAX_VALUE})
    void rejectsScoresOutsideTheAllowedRange(int score) {
        assertThrows(IllegalArgumentException.class,
                () -> new Review(1, 2, "A useful review", score));
    }

    @ParameterizedTest
    @MethodSource("invalidTexts")
    void rejectsMissingBlankOrOversizedText(String text) {
        assertThrows(IllegalArgumentException.class,
                () -> new Review(1, 2, text, 5));
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 10000})
    void acceptsTextAtBothLengthBoundaries(int length) {
        String text = "a".repeat(length);

        assertDoesNotThrow(() -> new Review(1, 2, text, 5));
    }

    @ParameterizedTest
    @CsvSource({"-1, 2", "1, -1"})
    void rejectsInvalidBookAndUserIds(int bookId, int userId) {
        assertThrows(IllegalArgumentException.class,
                () -> new Review(bookId, userId, "A useful review", 5));
    }

    @Test
    void allArgsConstructorAlsoValidatesItsArguments() {
        assertThrows(IllegalArgumentException.class,
                () -> new Review(-1, 1, 2, "Text", 5));
        assertThrows(IllegalArgumentException.class,
                () -> new Review(7, -1, 2, "Text", 5));
        assertThrows(IllegalArgumentException.class,
                () -> new Review(7, 1, -1, "Text", 5));
        assertThrows(IllegalArgumentException.class,
                () -> new Review(7, 1, 2, " ", 5));
        assertThrows(IllegalArgumentException.class,
                () -> new Review(7, 1, 2, "Text", 11));
    }

    @Test
    void allowsChangingScoreToBothBoundaries() {
        Review review = new Review(1, 2, "Text", 5);

        review.setScore(0);
        assertEquals(0, review.getScore());
        review.setScore(10);
        assertEquals(10, review.getScore());
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 11, Integer.MIN_VALUE, Integer.MAX_VALUE})
    void invalidScoreUpdatePreservesPreviousValue(int score) {
        Review review = new Review(1, 2, "Text", 5);

        assertThrows(IllegalArgumentException.class, () -> review.setScore(score));
        assertEquals(5, review.getScore());
    }

    @Test
    void acceptsTextUpdatesWithoutLosingLineBreaks() {
        Review review = new Review(1, 2, "Original text", 5);
        String text = "First line\nSecond line";

        review.setText(text);

        assertEquals(text, review.getText());
    }

    @ParameterizedTest
    @MethodSource("invalidTexts")
    void invalidTextUpdatePreservesPreviousValue(String text) {
        Review review = new Review(1, 2, "Original text", 5);

        assertThrows(IllegalArgumentException.class, () -> review.setText(text));
        assertEquals("Original text", review.getText());
    }

    private static Stream<String> invalidTexts() {
        return Stream.of(null, "", " ", " \t\n", "a".repeat(10001));
    }
}
