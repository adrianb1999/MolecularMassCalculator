package com.adrian99.molecularMassCalculator.calculator;

import com.adrian99.molecularMassCalculator.dto.FormulaDto;

import java.util.List;
import java.util.Map;

public interface MassCalculator {
    Map<String, List<FormulaDto>> startCalculator(String formula);
}
