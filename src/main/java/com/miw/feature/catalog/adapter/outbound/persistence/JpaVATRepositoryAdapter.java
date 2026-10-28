package com.miw.feature.catalog.adapter.outbound.persistence;

import com.miw.feature.catalog.core.domain.VAT;
import com.miw.feature.catalog.core.port.outbound.persistence.VATRepository;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

public class JpaVATRepositoryAdapter implements VATRepository {

    // Dependencies

    private final EntityManager entityManager;

    // All args constructor

    public JpaVATRepositoryAdapter(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    // Business methods

    @Override
    public List<VAT> getVATs() {
        return entityManager.createQuery("select v from VAT v order by v.taxGroup, v.id", VAT.class).getResultList();
    }

    @Override
    public Optional<VAT> findVATById(int vatId) {
        return Optional.ofNullable(entityManager.find(VAT.class, vatId));
    }
}
