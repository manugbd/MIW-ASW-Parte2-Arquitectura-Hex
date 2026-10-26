package com.miw.presentation.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.miw.feature.catalog.core.port.inbound.GetCatalogBooks;
import com.miw.feature.catalog.core.port.inbound.dto.BookDataDTO;

@Controller
public class ShowBooksController {

	// The inbound port that will be used.
	private GetCatalogBooks getCatalogBooks;

	// We inyect the inbound port through the constructor.
	public ShowBooksController(GetCatalogBooks getCatalogBooks) {
		this.getCatalogBooks = getCatalogBooks;
	}

	@GetMapping("/showBooks") // Gets executed when receiving any petition to the /showBooks url.
	public String showBooks(Model model) {

		// Request the books to our application through the inbound port.
		List<BookDataDTO> books = getCatalogBooks.getCatalogBooks();

		// Add the books to the model. This object will be sent to the page.
		model.addAttribute("books", books);

		// Render the showBooks.jsp page
		// The the data of the model will be available through the "books" name.
		return "showBooks";
	}
}
