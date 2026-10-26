package com.miw.feature.catalog.core;

import java.util.ArrayList;
import java.util.List;

import com.miw.feature.catalog.core.domain.Book;
import com.miw.feature.catalog.core.domain.VAT;

public class DomainHelper {
	public static VAT createVAT(int id, double value, int taxGroup) {
		VAT vat = new VAT();
		vat.setId(id);
		vat.setValue(value);
		vat.setTaxGroup(taxGroup);

		return vat;
	}

	public static Book createBook(int id, String title, String author, String description, double basePrice, VAT vat) {
		Book book = new Book();
		book.setId(id);
		book.setTitle(title);
		book.setAuthor(author);
		book.setDescription(description);
		book.setBasePrice(basePrice);
		book.setVat(vat);

		return book;
	}

	public static List<Book> createBookList(String[] titles, String[] authors, String[] descriptions,
			double[] basePrices, double[] vatValues) {

		List<Book> books = new ArrayList<Book>();
		for (int i = 0; i < titles.length; i++) {

			VAT vat = new VAT();
			vat.setId(i + 1);
			vat.setValue(vatValues[i]);
			vat.setTaxGroup(i + 1);

			Book book = new Book();
			book.setId(i + 1);
			book.setTitle(titles[i]);
			book.setAuthor(authors[i]);
			book.setDescription(descriptions[i]);
			book.setBasePrice(basePrices[i]);
			book.setVat(vat);

			books.add(book);
		}

		return books;
	}
}
