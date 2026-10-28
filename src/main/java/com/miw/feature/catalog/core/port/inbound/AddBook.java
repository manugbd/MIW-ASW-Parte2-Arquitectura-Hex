package com.miw.feature.catalog.core.port.inbound;

import com.miw.feature.catalog.core.port.inbound.dto.BookDataDTO;

public interface AddBook {

    BookDataDTO addBook(String title, String author, String description, double basePrice, int vatId);
}
