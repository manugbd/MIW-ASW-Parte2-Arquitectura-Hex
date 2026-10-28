package com.miw.feature.review.application;

import com.miw.feature.review.core.application.AddReviewInteractor;
import com.miw.feature.review.core.domain.Review;
import com.miw.feature.review.core.port.inbound.dto.ReviewDTO;
import com.miw.feature.review.core.port.inbound.exception.BookNotFoundException;
import com.miw.feature.review.core.port.inbound.exception.DuplicateReviewException;
import com.miw.feature.review.core.port.outbound.catalog.BookCatalog;
import com.miw.feature.review.core.port.outbound.catalog.BookForCartDTO;
import com.miw.feature.review.core.port.outbound.persistence.ReviewRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.ArgumentCaptor;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class AddReviewInteractorTest {

    private final ReviewRepository repository = mock(ReviewRepository.class);
    private final BookCatalog catalog = mock(BookCatalog.class);
    private final AddReviewInteractor interactor = new AddReviewInteractor(repository, catalog);

    private void existingBook(int bookId) {
        when(catalog.findBookById(bookId)).thenReturn(Optional.of(new BookForCartDTO(bookId, "Book")));
    }

    private void successfulSave() {
        when(repository.save(any(Review.class))).thenAnswer(invocation -> {
            Review review = invocation.getArgument(0);
            return new Review(7, review.getBookId(), review.getUserId(), review.getText(), review.getScore());
        });
    }

    @Test
    void savesReviewForRequestedBookAndUserAndReturnsGeneratedId() {
        existingBook(1);
        successfulSave();

        ReviewDTO result = interactor.addReview(2, 1, "Excellent", 8);

        ArgumentCaptor<Review> captured = ArgumentCaptor.forClass(Review.class);
        verify(repository).save(captured.capture());

        Review review = captured.getValue();
        assertEquals(1, review.getBookId());
        assertEquals(2, review.getUserId());
        assertEquals("Excellent", review.getText());
        assertEquals(8, review.getScore());
        assertEquals(new ReviewDTO(7, 2, "Excellent", 8), result);
        verify(repository).existsByBookIdAndUserId(1, 2);
    }

    @Test
    void rejectsSecondReviewForSameUserAndBook() {
        existingBook(1);
        when(repository.existsByBookIdAndUserId(1, 2)).thenReturn(true);
        assertThrows(DuplicateReviewException.class, () -> interactor.addReview(2, 1, "Text", 5));
        verify(repository, never()).save(any());
    }

    @Test
    void differentUsersCanReviewSameBook() {
        existingBook(1);
        successfulSave();
        assertEquals(2, interactor.addReview(2, 1, "First review", 8).userId());
        assertEquals(3, interactor.addReview(3, 1, "Second review", 6).userId());
        verify(repository).existsByBookIdAndUserId(1, 2);
        verify(repository).existsByBookIdAndUserId(1, 3);
    }

    @Test
    void sameUserCanReviewDifferentBooks() {
        existingBook(1);
        existingBook(2);

        successfulSave();
        interactor.addReview(3, 1, "First book", 8);
        interactor.addReview(3, 2, "Second book", 6);

        verify(repository).existsByBookIdAndUserId(1, 3);
        verify(repository).existsByBookIdAndUserId(2, 3);
        verify(repository, times(2)).save(any(Review.class));
    }

    @Test
    void unknownBookPreventsReviewPersistence() {
        when(catalog.findBookById(99)).thenReturn(Optional.empty());
        assertThrows(BookNotFoundException.class, () -> interactor.addReview(2, 99, "Text", 5));
        verifyNoInteractions(repository);
    }

    @ParameterizedTest
    @CsvSource({"-1, 1", "2, -1"})
    void rejectsNegativeIdsBeforeCallingPorts(int userId, int bookId) {
        assertThrows(IllegalArgumentException.class, () -> interactor.addReview(userId, bookId, "Text", 5));
        verifyNoInteractions(repository, catalog);
    }

    @Test
    void invalidReviewIsNotSaved() {
        existingBook(1);
        assertThrows(IllegalArgumentException.class, () -> interactor.addReview(2, 1, "Text", 11));
        verify(repository, never()).save(any());
    }

    @Test
    void acceptsZeroUserAndBookIds() {
        existingBook(0);
        successfulSave();
        assertEquals(0, interactor.addReview(0, 0, "Text", 0).userId());
        verify(repository).existsByBookIdAndUserId(0, 0);
    }
}
