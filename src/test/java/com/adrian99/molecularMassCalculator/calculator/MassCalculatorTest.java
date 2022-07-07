package com.adrian99.molecularMassCalculator.calculator;

import com.adrian99.molecularMassCalculator.calculator.implementation.MassCalculatorImpl;
import com.adrian99.molecularMassCalculator.dto.FormulaDto;
import com.adrian99.molecularMassCalculator.exception.InvalidFormulaException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class MassCalculatorTest {


    private final MassCalculator massCalculator = new MassCalculatorImpl();

    @Test
    void passWhenValidFormula() {
        List<FormulaDto> formulaDtoList = new ArrayList<>();
        formulaDtoList.add(new FormulaDto("H2O", 18.0152D));

        Map<String, List<FormulaDto>> responseMap = new HashMap<>();
        responseMap.put("formulas", formulaDtoList);

        assertThat(massCalculator.startCalculator("H2O"))
               .isEqualTo(responseMap);
    }

    @Test
    void failWhenInvalidFormula(){
        assertThrows(InvalidFormulaException.class,
                () -> massCalculator.startCalculator("aa"));
    }
}