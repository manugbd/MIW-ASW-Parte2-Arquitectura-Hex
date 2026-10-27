package com.miw.feature.viewed.core.port.outbound.history;

import java.util.List;

public interface ViewedBooksHistory {

    void recordView(int bookId);

    List<Integer> getViewedBookIds();
}
