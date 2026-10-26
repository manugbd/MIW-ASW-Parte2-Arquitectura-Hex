package com.miw.feature.catalog.adapter.outbound.persistence;

import java.util.List;
import java.util.Optional;

import com.miw.feature.catalog.core.domain.Book;
import com.miw.feature.catalog.core.port.outbound.persistence.BookRepository;

import jakarta.persistence.EntityManager;

public class JpaBookRepositoryAdapter implements BookRepository {

	private EntityManager entityManager;

	public JpaBookRepositoryAdapter(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public List<Book> getBooks() {

		try {
			return entityManager.createQuery("select b from Book b", Book.class).getResultList();
		} finally {
			entityManager.close();
		}
	}
	@Override
	public Optional<Book> findBookById(int bookId) {
		return Optional.ofNullable(entityManager.find(Book.class, bookId));
	}

}
