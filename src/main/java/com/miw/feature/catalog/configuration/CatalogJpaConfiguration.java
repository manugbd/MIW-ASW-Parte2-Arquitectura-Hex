package com.miw.feature.catalog.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.miw.feature.catalog.adapter.outbound.persistence.JpaBookRepositoryAdapter;
import com.miw.feature.catalog.core.port.outbound.persistence.BookRepository;

import jakarta.persistence.EntityManager;
import com.miw.feature.catalog.adapter.outbound.persistence.JpaVATRepositoryAdapter;
import com.miw.feature.catalog.core.port.outbound.persistence.VATRepository;

@Configuration
@Profile("jpa")
public class CatalogJpaConfiguration {

	@Bean
	public VATRepository vatRepository(EntityManager entityManager) {
		return new JpaVATRepositoryAdapter(entityManager);
	}

	@Bean
	public BookRepository bookRepository(EntityManager entityManager) {
		return new JpaBookRepositoryAdapter(entityManager);
	}

}
