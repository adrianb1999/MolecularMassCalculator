package com.adrian99.molecularMassCalculator.controller;

import com.adrian99.molecularMassCalculator.calculator.MassCalculator;
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
    public List<Map<String, Object>> calculator(@RequestParam(name = "formula") String molecularFormula) {

        return massCalculator.startCalculator(molecularFormula);
    }
}
