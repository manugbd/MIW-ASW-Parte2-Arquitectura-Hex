package com.miw.feature.catalog.core.application;

import com.miw.feature.catalog.core.port.inbound.GetVATs;
import com.miw.feature.catalog.core.port.inbound.dto.VATDataDTO;
import com.miw.feature.catalog.core.port.outbound.persistence.VATRepository;

import java.util.List;

public class GetVATsInteractor implements GetVATs {

    // Dependencies

    private final VATRepository vatRepository;

    // All args constructor

    public GetVATsInteractor(VATRepository vatRepository) {
        this.vatRepository = vatRepository;
    }

    // Business methods

    @Override
    public List<VATDataDTO> getVATs() {
        return vatRepository.getVATs().stream()
                .map(vat -> new VATDataDTO(
                        vat.getId(),
                        vat.getTaxGroup(),
                        vat.getValue()
                )).toList();
    }
}
