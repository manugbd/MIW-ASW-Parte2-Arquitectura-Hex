package com.miw.feature.catalog.core.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Objects;

@Entity
@Table(name = "VAT")
public class VAT {

    // Attributes

    @Id
    @Column(name = "ID")
    private int id;

    @Column(name = "VALUE", nullable = false)
    private double value;

    @Column(name = "TAXGROUP", nullable = false)
    private int taxGroup;

    // Empty constructor

    protected VAT() {
    }

    // All args constructor

    public VAT(int id, double value, int taxGroup) {
        setId(id);
        setValue(value);
        setTaxGroup(taxGroup);
    }

    // Getters

    public int getId() {
        return id;
    }

    public double getValue() {
        return value;
    }

    public int getTaxGroup() {
        return taxGroup;
    }

    // Setters

    private void setId(int id) {
        if (id < 0) throw new IllegalArgumentException("ID must not be negative");
        this.id = id;
    }

    public void setValue(double value) {
        if (value < 0) throw new IllegalArgumentException("VAT rate must be non-negative");
        this.value = value;
    }

    public void setTaxGroup(int taxGroup) {
        if (taxGroup <= 0) throw new IllegalArgumentException("Tax group must be positive");
        this.taxGroup = taxGroup;
    }


    // Equals and hashCode

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        VAT vat = (VAT) o;
        return getId() == vat.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
}
