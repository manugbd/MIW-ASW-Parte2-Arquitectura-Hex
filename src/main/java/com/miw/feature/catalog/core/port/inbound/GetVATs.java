package com.miw.feature.catalog.core.port.inbound;

import com.miw.feature.catalog.core.port.inbound.dto.VATDataDTO;

import java.util.List;

public interface GetVATs {

    List<VATDataDTO> getVATs();
}
