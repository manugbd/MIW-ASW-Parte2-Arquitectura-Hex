package com.miw.presentation.controllers;

import com.miw.feature.authentication.core.port.inbound.AuthenticatedUser;
import com.miw.feature.catalog.core.port.inbound.FindBook;
import com.miw.feature.catalog.core.port.inbound.dto.BookDataDTO;
import com.miw.feature.review.core.port.inbound.AddBookReview;
import com.miw.feature.review.core.port.inbound.GetBookReviews;
import com.miw.feature.review.core.port.inbound.dto.ReviewDTO;
import com.miw.feature.review.core.port.inbound.exception.BookNotFoundException;
import com.miw.feature.review.core.port.inbound.exception.DuplicateReviewException;
import com.miw.presentation.form.ReviewForm;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/books/{bookId}")
public class ReviewController {

    // Dependencies
    private final FindBook findBook;
    private final GetBookReviews getBookReviews;
    private final AddBookReview addBookReview;

    // All args constructor
    public ReviewController(FindBook findBook,
                            GetBookReviews getBookReviews,
                            AddBookReview addBookReview) {
        this.findBook = findBook;
        this.getBookReviews = getBookReviews;
        this.addBookReview = addBookReview;
    }

    // Business methods

    @GetMapping
    public String showBook(@PathVariable int bookId,
                           HttpSession session,
                           Model model) {

        authenticatedUser(session);

        model.addAttribute("reviewForm", new ReviewForm());
        return prepareBookDetails(bookId, model);
    }

    @PostMapping("/reviews")
    public String addReview(@PathVariable int bookId,
                            @ModelAttribute("reviewForm") ReviewForm form,
                            BindingResult bindingResult,
                            HttpSession session,
                            Model model) {
        AuthenticatedUser user = authenticatedUser(session);

        if (!bindingResult.hasErrors()) {
            if (form.getText() == null || form.getText().isBlank())
                bindingResult.rejectValue("text", "review.text.required", "El texto es obligatorio");

            if (form.getScore() == null)
                bindingResult.rejectValue("score", "review.score.required", "La puntuación es obligatoria");
        }

        if (bindingResult.hasErrors()) return prepareBookDetails(bookId, model);

        try {
            addBookReview.addReview(user.id(), bookId, form.getText(), form.getScore());
        } catch (DuplicateReviewException exception) {
            bindingResult.reject("review.duplicate", "Ya has escrito una reseña de este libro");

            return prepareBookDetails(bookId, model);
        } catch (IllegalArgumentException exception) {
            bindingResult.reject("review.invalid", exception.getMessage());
            return prepareBookDetails(bookId, model);
        }

        return "redirect:/books/" + bookId;
    }

    // Helper methods

    private String prepareBookDetails(int bookId, Model model) {
        if (bookId <= 0) throw new IllegalArgumentException("El identificador del libro debe ser positivo");

        BookDataDTO book = findBook.findBook(bookId).orElseThrow(() -> new BookNotFoundException(bookId));

        List<ReviewDTO> reviews = getBookReviews.getReviewsByBook(bookId);

        model.addAttribute("book", book);
        model.addAttribute("bookReviews", reviews);

        return "bookDetails";
    }

    private AuthenticatedUser authenticatedUser(HttpSession session) {
        if (session == null || !(session.getAttribute("authenticatedUser") instanceof AuthenticatedUser user))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Es necesario autenticarse");

        return user;
    }

    // Exception handlers

    @ExceptionHandler(BookNotFoundException.class)
    public void bookNotFound(BookNotFoundException exception, HttpServletResponse response) throws IOException {

        response.sendError(HttpServletResponse.SC_NOT_FOUND, exception.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public void invalidRequest(IllegalArgumentException exception, HttpServletResponse response) throws IOException {

        response.sendError(HttpServletResponse.SC_BAD_REQUEST, exception.getMessage());
    }
}