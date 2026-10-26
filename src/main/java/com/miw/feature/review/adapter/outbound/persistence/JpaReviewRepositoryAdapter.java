package com.miw.feature.review.adapter.outbound.persistence;

import java.util.List;
import java.util.Optional;
import java.util.Locale;
import com.miw.feature.review.core.domain.Review;
import com.miw.feature.review.core.port.inbound.exception.DuplicateReviewException;
import com.miw.feature.review.core.port.outbound.persistence.ReviewRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.transaction.annotation.Transactional;

public class JpaReviewRepositoryAdapter implements ReviewRepository {

    // Constants

    private static final String UNIQUE_USER_BOOK = "UK_BOOK_REVIEW_USER_BOOK";

    // Attributes

    private final EntityManager entityManager;

    // All args constructor

    public JpaReviewRepositoryAdapter(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    // Business methods

    @Override
    @Transactional(readOnly = true)
    public List<Review> findReviewsByBook(int bookId) {
        return entityManager.createQuery(
                "select r from Review r where r.bookId = :bookId order by r.id", Review.class)
                .setParameter("bookId", bookId).getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByBookIdAndUserId(int bookId, int userId) {
        return entityManager.createQuery(
                "select count(r) from Review r where r.bookId = :bookId and r.userId = :userId", Long.class)
                .setParameter("bookId", bookId).setParameter("userId", userId).getSingleResult() > 0;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Review> findById(int reviewId) {
        return Optional.ofNullable(entityManager.find(Review.class, reviewId));
    }

    @Override
    @Transactional
    public Review save(Review review) {
        try {
            entityManager.persist(review);
            entityManager.flush();
            return review;
        } catch (PersistenceException exception) {
            for (Throwable cause = exception; cause != null; cause = cause.getCause()) {
                if (cause instanceof ConstraintViolationException violation
                        && violation.getConstraintName() != null
                        && violation.getConstraintName().toUpperCase(Locale.ROOT).contains(UNIQUE_USER_BOOK)) {
                    throw new DuplicateReviewException(exception);
                }
            }
            throw exception;
        }
    }
}
