package com.miw.feature.shoppingcart.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.miw.event.EventPublisher;
import com.miw.feature.catalog.core.port.inbound.FindBook;
import com.miw.feature.shoppingcart.adapter.outbound.catalog.CatalogBookAdapter;
import com.miw.feature.shoppingcart.core.application.AddBookToCartInteractor;
import com.miw.feature.shoppingcart.core.application.DecreaseBookQuantityInteractor;
import com.miw.feature.shoppingcart.core.application.GetShoppingCartInteractor;
import com.miw.feature.shoppingcart.core.application.RemoveBookFromCartInteractor;
import com.miw.feature.shoppingcart.core.port.inbound.AddBookToCart;
import com.miw.feature.shoppingcart.core.port.inbound.DecreaseBookQuantity;
import com.miw.feature.shoppingcart.core.port.inbound.GetShoppingCart;
import com.miw.feature.shoppingcart.core.port.inbound.RemoveBookFromCart;
import com.miw.feature.shoppingcart.core.port.outbound.catalog.BookCatalog;
import com.miw.feature.shoppingcart.core.port.outbound.persistence.ShoppingCartRepository;

@Configuration
public class ShoppingCartConfiguration {

	@Bean
	public BookCatalog bookCatalog(FindBook findBook) {
		return new CatalogBookAdapter(findBook);
	}

	@Bean
	public AddBookToCart addBookToCart(ShoppingCartRepository shoppingCartRepository, BookCatalog bookCatalog,
			EventPublisher eventPublisher) {

		return new AddBookToCartInteractor(shoppingCartRepository, bookCatalog, eventPublisher);
	}

	@Bean
	public DecreaseBookQuantity decreaseBookQuantity(ShoppingCartRepository shoppingCartRepository) {

		return new DecreaseBookQuantityInteractor(shoppingCartRepository);
	}

	@Bean
	public RemoveBookFromCart removeBookFromCart(ShoppingCartRepository shoppingCartRepository) {

		return new RemoveBookFromCartInteractor(shoppingCartRepository);
	}

	@Bean
	public GetShoppingCart getShoppingCart(ShoppingCartRepository shoppingCartRepository, BookCatalog bookCatalog) {

		return new GetShoppingCartInteractor(shoppingCartRepository, bookCatalog);
	}
}
