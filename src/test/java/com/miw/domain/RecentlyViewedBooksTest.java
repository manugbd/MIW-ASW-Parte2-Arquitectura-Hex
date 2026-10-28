package com.miw.domain;

import com.miw.feature.viewed.core.domain.RecentlyViewedBooks;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RecentlyViewedBooksTest {

    @Test
    void keepsOnlyThreeMostRecentBooks() {
        RecentlyViewedBooks history = new RecentlyViewedBooks();
        assertTrue(history.getBookIds().isEmpty());
        for (int id = 1; id <= 4; id++) {
            history.recordView(id);
        }
        assertEquals(List.of(4, 3, 2), history.getBookIds());
    }

    @Test
    void repeatedBookMovesToFrontWithoutRemovingOtherBooks() {
        RecentlyViewedBooks history = new RecentlyViewedBooks();
        history.recordView(1);
        history.recordView(2);
        history.recordView(3);
        history.recordView(1);
        assertEquals(List.of(1, 3, 2), history.getBookIds());
        history.recordView(1);
        assertEquals(List.of(1, 3, 2), history.getBookIds());
    }

    @Test
    void historiesDoNotShareStateAndCannotBeModifiedExternally() {
        RecentlyViewedBooks first = new RecentlyViewedBooks();
        RecentlyViewedBooks second = new RecentlyViewedBooks();
        first.recordView(1);
        assertTrue(second.getBookIds().isEmpty());
        assertThrows(UnsupportedOperationException.class, () -> first.getBookIds().add(2));
        assertThrows(IllegalArgumentException.class, () -> first.recordView(-1));
        assertEquals(List.of(1), first.getBookIds());
    }

    @Test
    void acceptsZeroIdAndDoesNotDuplicateIt() {
        RecentlyViewedBooks history = new RecentlyViewedBooks();
        history.recordView(0);
        history.recordView(1);
        history.recordView(0);
        assertEquals(List.of(0, 1), history.getBookIds());
    }

    @Test
    void returnedListsAreSnapshots() {
        RecentlyViewedBooks history = new RecentlyViewedBooks();
        history.recordView(1);
        List<Integer> snapshot = history.getBookIds();
        history.recordView(2);
        assertEquals(List.of(1), snapshot);
        assertEquals(List.of(2, 1), history.getBookIds());
    }

    @Test
    void evictedBookCanBeVisitedAgain() {
        RecentlyViewedBooks history = new RecentlyViewedBooks();
        for (int id = 0; id < 10; id++) history.recordView(id);
        assertEquals(List.of(9, 8, 7), history.getBookIds());
        history.recordView(0);
        assertEquals(List.of(0, 9, 8), history.getBookIds());
    }
}
