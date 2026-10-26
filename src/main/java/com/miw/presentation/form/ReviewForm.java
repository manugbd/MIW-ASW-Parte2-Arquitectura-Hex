package com.miw.presentation.form;

public class ReviewForm {

    // Attributes

    private String text;
    private Integer score;

    // Empty constructor

    public ReviewForm() {
    }

    // All args constructor

    public ReviewForm(String text, Integer score) {
        setText(text);
        setScore(score);
    }

    // Getters

    public String getText() {
        return text;
    }

    public Integer getScore() {
        return score;
    }

    // Setters

    public void setText(String text) {
        if (text == null || text.isBlank() || text.length() > 10000)
            throw new IllegalArgumentException("Review text must contain between 1 and 10000 characters");
        this.text = text;
    }

    public void setScore(Integer score) {
        if (score == null || score < 0 || score > 10)
            throw new IllegalArgumentException("Score must be an integer between 0 and 10");
        this.score = score;
    }
}
