package com.miw.feature.viewed.core.port.outbound.bookcatalog;

import com.miw.feature.viewed.core.port.inbound.dto.BookDTO;

import java.util.List;

public interface ViewedBookCatalog {

    List<BookDTO> getBooks();

}
