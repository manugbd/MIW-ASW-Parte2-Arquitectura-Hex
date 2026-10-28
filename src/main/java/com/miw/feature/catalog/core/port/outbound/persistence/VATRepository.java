package com.miw.feature.catalog.core.port.outbound.persistence;

import com.miw.feature.catalog.core.domain.VAT;

import java.util.List;
import java.util.Optional;

public interface VATRepository {

    List<VAT> getVATs();

    Optional<VAT> findVATById(int vatId);
}
