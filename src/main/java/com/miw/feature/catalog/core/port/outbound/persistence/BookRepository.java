package com.miw.feature.catalog.core.port.outbound.persistence;

import java.util.List;
import java.util.Optional;

import com.miw.feature.catalog.core.domain.Book;

public interface BookRepository {

	List<Book> getBooks();

	Optional<Book> findBookById(int bookId);

}
