package com.miw.feature.catalog.adapter.outbound.bookselector;

import java.util.List;

import com.miw.feature.catalog.core.domain.Book;
import com.miw.feature.catalog.core.port.outbound.bookselector.BookSelector;

public class FirstBookSelector implements BookSelector {

	@Override
	public Book selectBook(List<Book> books) {
		if (books.isEmpty()) {
			return null;
		}
		Book book = books.get(0);
		return book;
	}

}
