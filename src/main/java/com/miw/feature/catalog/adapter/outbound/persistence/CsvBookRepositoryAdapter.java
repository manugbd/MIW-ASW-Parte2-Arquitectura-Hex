package com.miw.feature.catalog.adapter.outbound.persistence;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.miw.feature.catalog.core.domain.Book;
import com.miw.feature.catalog.core.domain.VAT;
import com.miw.feature.catalog.core.port.outbound.persistence.BookRepository;

public class CsvBookRepositoryAdapter implements BookRepository {

	private InputStream readFile() {
		InputStream input = getClass().getClassLoader().getResourceAsStream("data/books.csv");

		if (input == null) {
			throw new IllegalStateException("Could not find data/books.csv");
		}

		return input;
	}

	private Book csvLineToBook(String csvLine) {
		String[] fields = csvLine.split(";");

		VAT vat = new VAT(
				Integer.parseInt(fields[5]),
				Double.parseDouble(fields[6]),
				Integer.parseInt(fields[7]));

		return new Book(
				Integer.parseInt(fields[0]),
				fields[1],
				fields[2],
				fields[3],
				Double.parseDouble(fields[4]),
				vat);
	}

	@Override
	public List<Book> getBooks() {

		InputStream input = readFile();

		List<Book> books = new ArrayList<>();

		try (BufferedReader reader = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8))) {
			// Skip header
			reader.readLine();

			String line;
			while ((line = reader.readLine()) != null) {
				Book book = csvLineToBook(line);
				books.add(book);
			}

		} catch (Exception e) {
			throw new IllegalStateException("Error reading books.csv", e);
		}

		return books;
	}
	@Override
	public Optional<Book> findBookById(int bookId) {
		return getBooks().stream()
				.filter(book -> book.getId() == bookId)
				.findFirst();
	}

	@Override
	public Book add(Book book) {
		throw new UnsupportedOperationException("The CSV catalog is read-only. Use the jpa profile to add books.");
	}

}
