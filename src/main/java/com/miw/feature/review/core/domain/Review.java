package com.miw.feature.review.core.domain;

import com.miw.feature.authentication.core.domain.User;
import com.miw.feature.catalog.core.domain.Book;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "BOOK_REVIEW",
        uniqueConstraints = @UniqueConstraint(
                name = "UK_BOOK_REVIEW_USER_BOOK",
                columnNames = {"USER_ID", "BOOK_ID"}
        )
)
public class Review {

    // Attributes

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @Column(name = "BOOK_ID", nullable = false, updatable = false)
    private int bookId;

    @Column(name = "USER_ID", nullable = false, updatable = false)
    private int userId;

    @Column(name = "REVIEW_TEXT", nullable = false, length = 10000)
    private String text;

    @Column(name = "SCORE", nullable = false)
    private int score;

    // Empty constructor

    protected Review() {
    }

    // All args constructor

    public Review(int id, int bookId, int userId, String text, int score) {
        setId(id);
        setBookId(bookId);
        setUserId(userId);
        setText(text);
        setScore(score);
    }

    public Review(int bookId, int userId, String text, int score) {
        this(0, bookId, userId, text, score);
    }

    // Getters

    public int getId() {
        return id;
    }

    public int getBookId() {
        return bookId;
    }

    public int getUserId() {
        return userId;
    }

    public String getText() {
        return text;
    }

    public int getScore() {
        return score;
    }

    // Setters

    private void setId(int id) {
        if (id < 0) throw new IllegalArgumentException("ID must not be negative");
        this.id = id;
    }

    private void setBookId(int bookId) {
        if (bookId <= 0) throw new IllegalArgumentException("Book ID must be valid");
        this.bookId = bookId;
    }

    private void setUserId(int userId) {
        if (userId <= 0) throw new IllegalArgumentException("User ID must be valid");
        this.userId = userId;
    }

    public void setText(String text) {
        if (text == null || text.isBlank() || text.length() > 10000)
            throw new IllegalArgumentException("Review text must contain between 1 and 10000 characters");
        this.text = text;
    }

    public void setScore(int score) {
        if (score < 0 || score > 10)
            throw new IllegalArgumentException("Score must be between 0 and 10");
        this.score = score;
    }


    // Equals and hashCode


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Review review = (Review) o;
        return getId() == review.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
}
