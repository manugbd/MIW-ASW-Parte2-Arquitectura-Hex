package com.miw.presentation.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import com.miw.feature.viewed.core.port.inbound.GetViewedBooks;

@Controller
public class HomeController {

	// Dependencies

	private final GetViewedBooks getViewedBooks;

	// All args constructor

	public HomeController(GetViewedBooks getViewedBooks) {
		this.getViewedBooks = getViewedBooks;
	}

	@GetMapping("/home")
	public String showHome(Model model) {
		model.addAttribute("viewedBooks", getViewedBooks.getViewedBooks());
		return "home";
	}
}
