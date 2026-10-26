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

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "BOOK_ID", nullable = false, updatable = false)
    private Book book;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "USER_ID", nullable = false, updatable = false)
    private User user;

    @Column(name = "REVIEW_TEXT", nullable = false, length = 10000)
    private String text;

    @Column(name = "SCORE", nullable = false)
    private int score;

    // Empty constructor

    protected Review() {
    }

    // All args constructor

    public Review(int id, Book book, User user, String text, int score) {
        setId(id);
        setBook(book);
        setUser(user);
        setText(text);
        setScore(score);
    }

    public Review(Book book, User user, String text, int score) {
        this(0, book, user, text, score);
    }

    // Getters

    public int getId() {
        return id;
    }

    public Book getBook() {
        return book;
    }

    public User getUser() {
        return user;
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

    private void setBook(Book book) {
        if (book == null) throw new IllegalArgumentException("Book is required");
        this.book = book;
    }

    private void setUser(User user) {
        if (user == null) throw new IllegalArgumentException("User is required");
        this.user = user;
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
