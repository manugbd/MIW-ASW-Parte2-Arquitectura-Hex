package com.miw.feature.catalog.core.port.inbound;

import java.util.Optional;

import com.miw.feature.catalog.core.port.inbound.dto.BookDataDTO;

public interface FindBook {

	Optional<BookDataDTO> findBook(int bookId);
}
