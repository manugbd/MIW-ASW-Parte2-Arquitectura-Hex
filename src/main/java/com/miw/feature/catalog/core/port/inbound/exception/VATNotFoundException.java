package com.miw.feature.catalog.core.port.inbound.exception;

public class VATNotFoundException extends IllegalArgumentException {

    public VATNotFoundException(int vatId) {
        super("VAT " + vatId + " does not exist");
    }
}
