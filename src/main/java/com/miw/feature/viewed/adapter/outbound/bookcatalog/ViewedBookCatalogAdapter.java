package com.miw.feature.viewed.adapter.outbound.bookcatalog;

import com.miw.feature.catalog.core.port.inbound.GetCatalogBooks;
import com.miw.feature.viewed.core.port.inbound.dto.BookDTO;
import com.miw.feature.viewed.core.port.outbound.bookcatalog.ViewedBookCatalog;

import java.util.List;

public class ViewedBookCatalogAdapter implements ViewedBookCatalog {

    private final GetCatalogBooks getCatalogBooks;

    public ViewedBookCatalogAdapter(GetCatalogBooks getCatalogBooks) {
        this.getCatalogBooks = getCatalogBooks;
    }

    @Override
    public List<BookDTO> getBooks() {
        return getCatalogBooks.getCatalogBooks().stream().map(
                bookDataDTO ->
                        new BookDTO(
                                bookDataDTO.id(),
                                bookDataDTO.title(),
                                bookDataDTO.author(),
                                bookDataDTO.description(),
                                bookDataDTO.price()
                        )
        ).toList();
    }

}
