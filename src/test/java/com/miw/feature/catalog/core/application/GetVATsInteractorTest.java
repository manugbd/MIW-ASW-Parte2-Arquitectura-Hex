package com.miw.feature.catalog.core.application;

import com.miw.feature.catalog.core.domain.VAT;
import com.miw.feature.catalog.core.port.inbound.dto.VATDataDTO;
import com.miw.feature.catalog.core.port.outbound.persistence.VATRepository;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class GetVATsInteractorTest {

    private final VATRepository repository = mock(VATRepository.class);
    private final GetVATsInteractor interactor = new GetVATsInteractor(repository);

    @Test
    void mapsExistingVatsWithoutConfusingIdAndTaxGroup() {
        when(repository.getVATs()).thenReturn(List.of(new VAT(9, 0.21, 2), new VAT(4, 0, 7)));
        assertEquals(List.of(new VATDataDTO(9, 2, 0.21), new VATDataDTO(4, 7, 0)), interactor.getVATs());
        verify(repository).getVATs();
        verifyNoMoreInteractions(repository);
    }

    @Test
    void handlesMissingVatGroups() {
        when(repository.getVATs()).thenReturn(List.of());
        assertTrue(interactor.getVATs().isEmpty());
    }
}
