package com.miw.feature.catalog.adapter.outbound.bookselector;

import java.util.List;

import com.miw.feature.catalog.core.domain.Book;
import com.miw.feature.catalog.core.port.outbound.bookselector.BookSelector;

public class RandomBookSelector implements BookSelector {

	@Override
	public Book selectBook(List<Book> books) {
		if (books.isEmpty()) {
			return null;
		}

		int index = (int) (Math.random() * books.size());
		Book book = books.get(index);
		return book;
	}

}
