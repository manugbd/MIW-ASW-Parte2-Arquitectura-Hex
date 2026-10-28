package com.miw.domain;

import com.miw.feature.catalog.core.domain.VAT;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class VATTest {

    @Test
    void rejectsNegativeIdRateAndTaxGroup() {
        assertThrows(IllegalArgumentException.class, () -> new VAT(-1, 0.21, 1));
        assertThrows(IllegalArgumentException.class, () -> new VAT(1, -0.01, 1));
        assertThrows(IllegalArgumentException.class, () -> new VAT(1, 0.21, -1));
    }

    @Test
    void invalidUpdatesPreservePreviousValues() {
        VAT vat = new VAT(1, 0.21, 2);
        assertThrows(IllegalArgumentException.class, () -> vat.setValue(-0.01));
        assertThrows(IllegalArgumentException.class, () -> vat.setTaxGroup(-1));
        assertEquals(0.21, vat.getValue());
        assertEquals(2, vat.getTaxGroup());
    }

    @Test
    void acceptsZeroVatAndChangesToAnotherTaxGroup() {
        VAT vat = new VAT(0, 0, 0);
        vat.setValue(0.04);
        vat.setTaxGroup(7);
        assertEquals(0.04, vat.getValue());
        assertEquals(7, vat.getTaxGroup());
        vat.setValue(0);
        assertEquals(0, vat.getValue());
    }
}
