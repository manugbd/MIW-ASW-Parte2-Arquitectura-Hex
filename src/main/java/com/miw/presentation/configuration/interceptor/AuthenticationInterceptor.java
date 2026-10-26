package com.miw.presentation.configuration.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class AuthenticationInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		// Get the current session without creating a new one.
		HttpSession session = request.getSession(false);

		// No authenticated use: redirect to the login page.
		if (session == null || session.getAttribute("authenticatedUser") == null) {

			response.sendRedirect(request.getContextPath() + "/");

			return false;
		}

		// The request may continue to the controller.
		return true;
	}
}