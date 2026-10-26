package com.miw.feature.review.core.port.outbound.catalog;

import java.util.Optional;

public interface BookCatalog {

	Optional<BookForCartDTO> findBookById(int bookId);
}
