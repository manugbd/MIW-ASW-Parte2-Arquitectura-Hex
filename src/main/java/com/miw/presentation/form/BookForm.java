package com.miw.presentation.form;

public class BookForm {

    // Attributes

    private String title;
    private String author;
    private String description;
    private Double basePrice;
    private Integer vatId;

    // Empty constructor

    public BookForm() {
    }

    // All args constructor

    public BookForm(String title, String author, String description, Double basePrice, Integer vatId) {
        setTitle(title);
        setAuthor(author);
        setDescription(description);
        setBasePrice(basePrice);
        setVatId(vatId);
    }

    // Getters

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getDescription() {
        return description;
    }

    public Double getBasePrice() {
        return basePrice;
    }

    public Integer getVatId() {
        return vatId;
    }

    // Setters

    public void setTitle(String title) {
        if (title == null || title.isBlank() || title.length() > 255)
            throw new IllegalArgumentException("Title must contain between 1 and 255 characters");
        this.title = title;
    }

    public void setAuthor(String author) {
        if (author == null || author.isBlank() || author.length() > 255)
            throw new IllegalArgumentException("Author must contain between 1 and 255 characters");
        this.author = author;
    }

    public void setDescription(String description) {
        if (description == null || description.isBlank() || description.length() > 255)
            throw new IllegalArgumentException("Description must contain between 1 and 255 characters");
        this.description = description;
    }

    public void setBasePrice(Double basePrice) {
        if (basePrice == null || !Double.isFinite(basePrice) || basePrice < 0)
            throw new IllegalArgumentException("Base price must be finite and non-negative");
        this.basePrice = basePrice;
    }

    public void setVatId(Integer vatId) {
        if (vatId == null || vatId < 0) throw new IllegalArgumentException("Select a valid VAT");
        this.vatId = vatId;
    }
}
