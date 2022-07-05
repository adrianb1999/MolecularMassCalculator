package com.adrian99.molecularMassCalculator.dto;

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
}
