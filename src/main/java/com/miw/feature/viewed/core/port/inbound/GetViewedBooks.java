package com.miw.feature.viewed.core.port.inbound;

import com.miw.feature.viewed.core.port.inbound.dto.BookDTO;

import java.util.List;

public interface GetViewedBooks {

    List<BookDTO> getViewedBooks();
}
