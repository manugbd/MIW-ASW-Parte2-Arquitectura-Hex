package com.miw.feature.shoppingcart.core.port.outbound.catalog;

import java.util.Optional;

public interface BookCatalog {

	Optional<BookForCartDTO> findBookById(int bookId);
}
