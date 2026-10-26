package com.miw.feature.catalog.core;

import java.util.ArrayList;
import java.util.List;

import com.miw.feature.catalog.core.domain.Book;
import com.miw.feature.catalog.core.domain.VAT;

public class DomainHelper {
	public static VAT createVAT(int id, double value, int taxGroup) {
		return new VAT(id, value, taxGroup);
	}

	public static Book createBook(int id, String title, String author, String description, double basePrice, VAT vat) {
		return new Book(id, title, author, description, basePrice, vat);
	}

	public static List<Book> createBookList(String[] titles, String[] authors, String[] descriptions,
			double[] basePrices, double[] vatValues) {

		List<Book> books = new ArrayList<>();
		for (int i = 0; i < titles.length; i++) {

			VAT vat = createVAT(i + 1, vatValues[i], i + 1);
			Book book = createBook(i + 1, titles[i], authors[i], descriptions[i], basePrices[i], vat);

			books.add(book);
		}

		return books;
	}
}
