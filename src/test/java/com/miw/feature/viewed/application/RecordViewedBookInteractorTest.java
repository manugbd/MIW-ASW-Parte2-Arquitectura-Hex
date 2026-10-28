package com.miw.feature.viewed.application;

import com.miw.feature.viewed.core.application.RecordViewedBookInteractor;

import com.miw.feature.viewed.core.port.outbound.history.ViewedBooksHistory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RecordViewedBookInteractorTest {
    private final ViewedBooksHistory history = mock(ViewedBooksHistory.class);
    private final RecordViewedBookInteractor interactor = new RecordViewedBookInteractor(history);

    @ParameterizedTest
    @ValueSource(ints = {0, 1, Integer.MAX_VALUE})
    void recordsRequestedBook(int bookId) {
        interactor.recordView(bookId);
        verify(history).recordView(bookId);
        verifyNoMoreInteractions(history);
    }

    @Test
    void rejectsNegativeIdsWithoutChangingHistory() {
        assertThrows(IllegalArgumentException.class, () -> interactor.recordView(-1));
        verifyNoInteractions(history);
    }
}
