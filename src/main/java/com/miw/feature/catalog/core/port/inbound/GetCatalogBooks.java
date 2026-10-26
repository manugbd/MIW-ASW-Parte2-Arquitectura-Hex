package com.miw.feature.catalog.core.port.inbound;

import java.util.List;

import com.miw.feature.catalog.core.port.inbound.dto.BookDataDTO;

public interface GetCatalogBooks {

	public List<BookDataDTO> getCatalogBooks();

}
