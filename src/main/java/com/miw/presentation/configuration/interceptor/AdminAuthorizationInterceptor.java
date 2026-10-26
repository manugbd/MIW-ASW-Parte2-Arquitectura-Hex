package com.miw.presentation.configuration.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.miw.feature.authentication.core.domain.Role;
import com.miw.feature.authentication.core.port.inbound.AuthenticatedUser;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class AdminAuthorizationInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		HttpSession session = request.getSession(false);

		AuthenticatedUser authenticatedUser = (AuthenticatedUser) session.getAttribute("authenticatedUser");

		if (authenticatedUser.role() != Role.ADMIN) {

			response.sendError(HttpServletResponse.SC_FORBIDDEN);

			return false;
		}

		return true;
	}
}