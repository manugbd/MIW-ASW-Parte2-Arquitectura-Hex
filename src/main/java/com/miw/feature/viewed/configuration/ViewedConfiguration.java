package com.miw.feature.viewed.configuration;

import com.miw.feature.catalog.core.port.inbound.GetCatalogBooks;
import com.miw.feature.viewed.adapter.outbound.bookcatalog.ViewedBookCatalogAdapter;
import com.miw.feature.viewed.adapter.outbound.history.SessionViewedBooksHistoryAdapter;
import com.miw.feature.viewed.core.application.GetViewedBooksInteractor;
import com.miw.feature.viewed.core.application.RecordViewedBookInteractor;
import com.miw.feature.viewed.core.domain.RecentlyViewedBooks;
import com.miw.feature.viewed.core.port.inbound.GetViewedBooks;
import com.miw.feature.viewed.core.port.inbound.RecordViewedBook;
import com.miw.feature.viewed.core.port.outbound.bookcatalog.ViewedBookCatalog;
import com.miw.feature.viewed.core.port.outbound.history.ViewedBooksHistory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.context.annotation.SessionScope;

@Configuration
public class ViewedConfiguration {

    @Bean
    @SessionScope(proxyMode = ScopedProxyMode.TARGET_CLASS)
    public RecentlyViewedBooks recentlyViewedBooks() {
        return new RecentlyViewedBooks();
    }

    @Bean
    public ViewedBooksHistory viewedBooksHistory(RecentlyViewedBooks history) {
        return new SessionViewedBooksHistoryAdapter(history);
    }

    @Bean
    public GetViewedBooks getViewedBooks(ViewedBookCatalog bookCatalog, ViewedBooksHistory history) {
        return new GetViewedBooksInteractor(bookCatalog, history);
    }

    @Bean
    public ViewedBookCatalog viewedBookCatalog(GetCatalogBooks getCatalogBooks) {
        return new ViewedBookCatalogAdapter(getCatalogBooks);
    }

    @Bean
    public RecordViewedBook recordViewedBook(ViewedBooksHistory history) {
        return new RecordViewedBookInteractor(history);
    }
}
