package com.miw.feature.catalog.core.port.outbound.bookselector;

import java.util.List;

import com.miw.feature.catalog.core.domain.Book;

public interface BookSelector {
	public Book selectBook(List<Book> books);
}