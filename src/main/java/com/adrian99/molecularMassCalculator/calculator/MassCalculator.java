package com.adrian99.molecularMassCalculator.calculator;

import java.util.List;
import java.util.Map;

public interface MassCalculator {
    List<Map<String, Object>> startCalculator(String formula);
}
