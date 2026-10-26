package com.miw.presentation.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.miw.feature.catalog.core.port.inbound.GetSpecialOfferBook;
import com.miw.feature.catalog.core.port.inbound.dto.BookDataDTO;

@Controller
public class ShowSpecialOfferController {

	private GetSpecialOfferBook getSpecialOfferBook;

	public ShowSpecialOfferController(GetSpecialOfferBook getSpecialOfferBook) {
		this.getSpecialOfferBook = getSpecialOfferBook;
	}

	@GetMapping("/showSpecialOffer")
	public String showSpecialOffer(Model model) {
		BookDataDTO specialOfferBook = getSpecialOfferBook.getSpecialOfferBook();

		model.addAttribute("specialOfferBook", specialOfferBook);

		return "showSpecialOffer";
	}

}
