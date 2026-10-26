package com.miw.feature.catalog.core.domain;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "BOOK")
public class Book {

    // Attributes

    @Id
    @Column(name = "ID")
    private int id;

    @Column(name = "TITLE", nullable = false)
    private String title;

    @Column(name = "AUTHOR", nullable = false)
    private String author;

    @Column(name = "DESCRIPTION", nullable = false)
    private String description;

    @Column(name = "BASEPRICE", nullable = false)
    private double basePrice;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "TAXGROUP", nullable = false)
    private VAT vat;

    // Empty constructor

    protected Book() {
    }

    // All args constructor

    public Book(int id, String title, String author, String description, double basePrice, VAT vat) {
        setId(id);
        setTitle(title);
        setAuthor(author);
        setDescription(description);
        setBasePrice(basePrice);
        setVat(vat);
    }

    // Getters

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getDescription() {
        return description;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public VAT getVat() {
        return vat;
    }

    // Setters

    private void setId(int id) {
        if (id < 0) throw new IllegalArgumentException("ID must not be negative");
        this.id = id;
    }

    public void setTitle(String title) {
        if (title == null || title.isBlank())
            throw new IllegalArgumentException("title must not be blank");
        this.title = title;
    }

    public void setAuthor(String author) {
        if (author == null || author.isBlank())
            throw new IllegalArgumentException("author must not be blank");
        this.author = author;
    }

    public void setDescription(String description) {
        if (description == null || description.isBlank())
            throw new IllegalArgumentException("description must not be blank");
        this.description = description;
    }

    public void setBasePrice(double basePrice) {
        if (basePrice < 0)
            throw new IllegalArgumentException("Base price must be finite and non-negative");
        this.basePrice = basePrice;
    }

    public void setVat(VAT vat) {
        if (vat == null) throw new IllegalArgumentException("VAT is required");
        this.vat = vat;
    }

    // Business methods

    public double getPrice() {
        return getBasePrice() * (1 + getVat().getValue());
    }

    public double getDiscountedPrice(double discount) {
        if (discount < 0 || discount > 1)
            throw new IllegalArgumentException("Price multiplier must be between 0 and 1");
        return getPrice() * discount;
    }

    // Equals and hashCode


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return getId() == book.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
}
