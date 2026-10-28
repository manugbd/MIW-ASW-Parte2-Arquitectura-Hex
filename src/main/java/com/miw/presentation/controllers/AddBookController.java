package com.miw.presentation.controllers;

import com.miw.feature.catalog.core.port.inbound.AddBook;
import com.miw.feature.catalog.core.port.inbound.GetVATs;
import com.miw.feature.catalog.core.port.inbound.dto.BookDataDTO;
import com.miw.feature.catalog.core.port.inbound.exception.VATNotFoundException;
import com.miw.presentation.form.BookForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/books")
public class AddBookController {

    // Dependencies

    private final AddBook addBook;
    private final GetVATs getVATs;

    // All args constructor

    public AddBookController(AddBook addBook, GetVATs getVATs) {
        this.addBook = addBook;
        this.getVATs = getVATs;
    }

    // Business methods

    @GetMapping("/new")
    public String showForm(Model model) {
        model.addAttribute("bookForm", new BookForm());
        return prepareForm(model);
    }

    @PostMapping
    public String addBook(@ModelAttribute("bookForm") BookForm form,
                          BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        requireField(form.getTitle(), "title", bindingResult);
        requireField(form.getAuthor(), "author", bindingResult);
        requireField(form.getDescription(), "description", bindingResult);
        requireField(form.getBasePrice(), "basePrice", bindingResult);
        requireField(form.getVatId(), "vatId", bindingResult);

        if (bindingResult.hasErrors()) return prepareForm(model);

        try {
            BookDataDTO createdBook = addBook.addBook(
                    form.getTitle(),
                    form.getAuthor(),
                    form.getDescription(),
                    form.getBasePrice(),
                    form.getVatId());
            redirectAttributes.addFlashAttribute("createdBook", createdBook);
        } catch (VATNotFoundException exception) {
            bindingResult.rejectValue("vatId", "book.vat.invalid", exception.getMessage());
            return prepareForm(model);
        } catch (IllegalArgumentException | UnsupportedOperationException exception) {
            bindingResult.reject("book.invalid", exception.getMessage());
            return prepareForm(model);
        }

        return "redirect:/admin/books/new";
    }

    // Helper methods

    private String prepareForm(Model model) {
        model.addAttribute("vats", getVATs.getVATs());
        return "addBook";
    }

    private void requireField(Object value, String field, BindingResult bindingResult) {
        if (value == null && !bindingResult.hasFieldErrors(field)) {
            bindingResult.rejectValue(field, "book.required", "This field is required");
        }
    }
}
