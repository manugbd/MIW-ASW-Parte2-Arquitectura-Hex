package com.miw.feature.review.application;

import com.miw.feature.review.core.application.GetBookReviewsInteractor;
import com.miw.feature.review.core.domain.Review;
import com.miw.feature.review.core.port.inbound.dto.ReviewDTO;
import com.miw.feature.review.core.port.outbound.persistence.ReviewRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GetBookReviewsInteractorTest {

    private final ReviewRepository repository = mock(ReviewRepository.class);
    private final GetBookReviewsInteractor interactor = new GetBookReviewsInteractor(repository);

    @Test
    void requestsOnlyTheSelectedBooksReviewsAndReturnsTheirPublicData() {
        when(repository.findReviewsByBook(4)).thenReturn(List.of(
                new Review(7, 4, 2, "Good", 8),
                new Review(9, 4, 3, "Excellent", 10)));

        List<ReviewDTO> result = interactor.getReviewsByBook(4);

        assertEquals(List.of(
                new ReviewDTO(7, 2, "Good", 8),
                new ReviewDTO(9, 3, "Excellent", 10)), result);
        verify(repository).findReviewsByBook(4);
        verifyNoMoreInteractions(repository);
    }

    @Test
    void returnsAnEmptyListForABookWithoutReviews() {
        when(repository.findReviewsByBook(4)).thenReturn(List.of());

        assertTrue(interactor.getReviewsByBook(4).isEmpty());
    }

    @ParameterizedTest
    @ValueSource(ints = {-10, -1})
    void rejectsInvalidBookIdWithoutAccessingPersistence(int bookId) {
        assertThrows(IllegalArgumentException.class,
                () -> interactor.getReviewsByBook(bookId));

        verifyNoInteractions(repository);
    }

    @Test
    void doesNotHidePersistenceFailuresAsAnEmptyList() {
        IllegalStateException failure = new IllegalStateException("Persistence unavailable");
        when(repository.findReviewsByBook(4)).thenThrow(failure);

        assertSame(failure, assertThrows(IllegalStateException.class,
                () -> interactor.getReviewsByBook(4)));
    }
}
