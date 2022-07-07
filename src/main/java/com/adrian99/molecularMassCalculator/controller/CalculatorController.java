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
    public Map<String, List<FormulaDto>> calculator(@RequestParam(name = "formula", required = false) String paramFormula,
                                                    @RequestBody(required=false) Map<String, String> formula) {
        String molecularFormula = "";

        if(formula != null && formula.containsKey("formula"))
            molecularFormula = formula.get("formula");
        else
            molecularFormula = paramFormula;

        if(molecularFormula == null || molecularFormula.isEmpty())
            throw new InvalidFormulaException("Invalid formula!");

        return massCalculator.startCalculator(molecularFormula);
    }
}
