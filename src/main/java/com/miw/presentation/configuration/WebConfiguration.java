package com.miw.presentation.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.miw.presentation.configuration.interceptor.AdminAuthorizationInterceptor;
import com.miw.presentation.configuration.interceptor.AuthenticationInterceptor;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {

	private final AuthenticationInterceptor authenticationInterceptor;
	private final AdminAuthorizationInterceptor adminAthorizationInterceptor;

	public WebConfiguration(AuthenticationInterceptor authenticationInterceptor,
			AdminAuthorizationInterceptor adminAthorizationInterceptor) {

		this.authenticationInterceptor = authenticationInterceptor;
		this.adminAthorizationInterceptor = adminAthorizationInterceptor;
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {

		registry.addInterceptor(authenticationInterceptor)
				.addPathPatterns(
						"/home",
						"/showBooks",
						"/showSpecialOffer",
						"/cart",
						"/cart/**",
						"/adminInfo");

		registry.addInterceptor(adminAthorizationInterceptor)
				.addPathPatterns("/adminInfo");
	}
}
