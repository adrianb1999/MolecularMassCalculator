package com.adrian99.molecularMassCalculator.controller;

import com.adrian99.molecularMassCalculator.calculator.MassCalculator;
import com.adrian99.molecularMassCalculator.dto.FormulaDto;
import com.adrian99.molecularMassCalculator.exception.InvalidFormulaException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class CalculatorController {

    private final MassCalculator massCalculator;

    public CalculatorController(MassCalculator massCalculator) {
        this.massCalculator = massCalculator;
    }

    @GetMapping("/api/massCalculator")
    public Map<String, List<FormulaDto>> calculator(@RequestBody(required=false) Map<String, String> formula) {
        if(formula == null || formula.isEmpty() || !formula.containsKey("formula") || formula.get("formula").isEmpty() || formula.get("formula").isBlank())
            throw new InvalidFormulaException("Invalid formula!");

        String molecularFormula = formula.get("formula");

        return massCalculator.startCalculator(molecularFormula);
    }
}
