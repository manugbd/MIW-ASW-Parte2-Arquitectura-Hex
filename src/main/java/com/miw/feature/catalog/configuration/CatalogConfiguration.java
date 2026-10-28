package com.miw.feature.catalog.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.miw.feature.catalog.adapter.outbound.bookselector.RandomBookSelector;
import com.miw.feature.catalog.core.application.FindBookInteractor;
import com.miw.feature.catalog.core.application.GetCatalogBooksInteractor;
import com.miw.feature.catalog.core.application.GetSpecialOfferBookInteractor;
import com.miw.feature.catalog.core.port.inbound.FindBook;
import com.miw.feature.catalog.core.port.inbound.GetCatalogBooks;
import com.miw.feature.catalog.core.port.inbound.GetSpecialOfferBook;
import com.miw.feature.catalog.core.port.outbound.bookselector.BookSelector;
import com.miw.feature.catalog.core.port.outbound.persistence.BookRepository;
import com.miw.feature.catalog.core.application.AddBookInteractor;
import com.miw.feature.catalog.core.application.GetVATsInteractor;
import com.miw.feature.catalog.core.port.inbound.AddBook;
import com.miw.feature.catalog.core.port.inbound.GetVATs;
import com.miw.feature.catalog.core.port.outbound.persistence.VATRepository;

@Configuration
public class CatalogConfiguration {

	@Bean
	public AddBook addBook(BookRepository bookRepository, VATRepository vatRepository) {
		return new AddBookInteractor(bookRepository, vatRepository);
	}

	@Bean
	public GetVATs getVATs(VATRepository vatRepository) {
		return new GetVATsInteractor(vatRepository);
	}

	@Bean
	public BookSelector bookSelector() {
		return new RandomBookSelector();
	}

	@Bean
	public FindBook findBook(BookRepository bookRepository) {
		return new FindBookInteractor(bookRepository);
	}

	@Bean
	public GetCatalogBooks getCatalogBooks(BookRepository bookRepository) {
		return new GetCatalogBooksInteractor(bookRepository);
	}

	@Bean
	public GetSpecialOfferBook getSpecialOfferBook(BookRepository bookRepository, BookSelector bookSelector) {
		return new GetSpecialOfferBookInteractor(bookRepository, bookSelector);
	}

}
