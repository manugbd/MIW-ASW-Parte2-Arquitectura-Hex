package com.miw.presentation.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.miw.feature.authentication.core.port.inbound.AuthenticatedUser;
import com.miw.feature.shoppingcart.core.port.inbound.AddBookToCart;
import com.miw.feature.shoppingcart.core.port.inbound.DecreaseBookQuantity;
import com.miw.feature.shoppingcart.core.port.inbound.GetShoppingCart;
import com.miw.feature.shoppingcart.core.port.inbound.RemoveBookFromCart;
import com.miw.feature.shoppingcart.core.port.inbound.dto.ShoppingCartDTO;

import jakarta.servlet.http.HttpSession;

@Controller
public class ShoppingCartController {

	private final GetShoppingCart getShoppingCart;
	private final AddBookToCart addBookToCart;
	private final DecreaseBookQuantity decreaseBookQuantity;
	private final RemoveBookFromCart removeBookFromCart;

	public ShoppingCartController(
			GetShoppingCart getShoppingCart,
			AddBookToCart addBookToCart,
			DecreaseBookQuantity decreaseBookQuantity,
			RemoveBookFromCart removeBookFromCart) {

		this.getShoppingCart = getShoppingCart;
		this.addBookToCart = addBookToCart;
		this.decreaseBookQuantity = decreaseBookQuantity;
		this.removeBookFromCart = removeBookFromCart;
	}

	@GetMapping("/cart")
	public String showCart(HttpSession session, Model model) {

		AuthenticatedUser user = (AuthenticatedUser) session
				.getAttribute("authenticatedUser");

		ShoppingCartDTO cart = getShoppingCart
				.getShoppingCart(user.id());

		model.addAttribute("cart", cart);

		return "shoppingCart";
	}

	@PostMapping("/cart/items")
	public String addBook(
			@RequestParam int bookId,
			HttpSession session) {

		AuthenticatedUser user = (AuthenticatedUser) session
				.getAttribute("authenticatedUser");

		addBookToCart.addBook(user.id(), bookId);

		return "redirect:/showBooks";
	}

	@PostMapping("/cart/items/{bookId}/decrease")
	public String decreaseBook(
			@PathVariable int bookId,
			HttpSession session) {

		AuthenticatedUser user = (AuthenticatedUser) session
				.getAttribute("authenticatedUser");

		decreaseBookQuantity.decreaseBookQuantity(
				user.id(), bookId);

		return "redirect:/cart";
	}

	@PostMapping("/cart/items/{bookId}/remove")
	public String removeBook(
			@PathVariable int bookId,
			HttpSession session) {

		AuthenticatedUser user = (AuthenticatedUser) session
				.getAttribute("authenticatedUser");

		removeBookFromCart.removeBook(user.id(), bookId);

		return "redirect:/cart";
	}
}
