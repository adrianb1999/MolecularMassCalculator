package com.adrian99.molecularMassCalculator.dto;

import java.util.Objects;

public class FormulaDto {

    private String formula;
    private Double mass;

    public FormulaDto(String formula, Double mass) {
        this.formula = formula;
        this.mass = mass;
    }

    public FormulaDto() {
    }

    public String getFormula() {
        return formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public Double getMass() {
        return mass;
    }

    public void setMass(Double mass) {
        this.mass = mass;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FormulaDto that = (FormulaDto) o;
        return Objects.equals(formula, that.formula) && Objects.equals(mass, that.mass);
    }

    @Override
    public int hashCode() {
        return Objects.hash(formula, mass);
    }
}
