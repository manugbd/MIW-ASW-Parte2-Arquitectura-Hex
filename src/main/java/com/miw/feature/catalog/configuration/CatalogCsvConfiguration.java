package com.miw.feature.catalog.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.miw.feature.catalog.adapter.outbound.persistence.CsvBookRepositoryAdapter;
import com.miw.feature.catalog.core.port.outbound.persistence.BookRepository;

@Configuration
@Profile("csv")
public class CatalogCsvConfiguration {

	@Bean
	public BookRepository bookRepository() {
		return new CsvBookRepositoryAdapter();
	}

}
