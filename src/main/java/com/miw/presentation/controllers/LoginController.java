package com.miw.presentation.controllers;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.miw.feature.authentication.core.port.inbound.AuthenticateUser;
import com.miw.feature.authentication.core.port.inbound.AuthenticatedUser;
import com.miw.presentation.form.LoginForm;

import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {

	private final AuthenticateUser authenticateUser;

	public LoginController(AuthenticateUser authenticateUser) {
		this.authenticateUser = authenticateUser;
	}

	@GetMapping("/")
	public String showLogin(HttpSession session) {

		// If the user is already authenticated,
		// go directly to the home page.
		if (session.getAttribute("authenticatedUser") != null) {
			return "redirect:/home";
		}

		return "login";
	}

	@PostMapping("/login")
	public String login(@ModelAttribute LoginForm loginForm, HttpSession session, Model model) {

		Optional<AuthenticatedUser> result = authenticateUser.authenticate(loginForm.getUsername(),
				loginForm.getPassword());

		if (result.isEmpty()) {
			model.addAttribute("error", true);

			return "login";
		}

		session.setAttribute("authenticatedUser", result.get());

		return "redirect:/home";
	}

	@PostMapping("/logout")
	public String logout(HttpSession session) {

		session.invalidate();

		return "redirect:/";
	}
}