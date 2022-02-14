package com.adrian99.molecularMassCalculator.controller;

import com.adrian99.molecularMassCalculator.calculator.MassCalculator;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class CalculatorController {

    private final MassCalculator massCalculator;

    public CalculatorController(MassCalculator massCalculator) {
        this.massCalculator = massCalculator;
    }

    @GetMapping("/api/massCalculator")
    public Map<String, Object> calculator(@RequestParam(name = "formula") String molecularFormula) {

        Map<String, Object> formulaMap = new HashMap<>();

        formulaMap.put("mass", massCalculator.calculate(molecularFormula));
        formulaMap.put("formula", molecularFormula);

        return formulaMap;
    }
}
