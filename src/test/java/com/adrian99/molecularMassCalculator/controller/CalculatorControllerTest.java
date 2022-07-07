package com.adrian99.molecularMassCalculator.controller;

import com.adrian99.molecularMassCalculator.calculator.MassCalculator;
import com.adrian99.molecularMassCalculator.dto.FormulaDto;
import com.adrian99.molecularMassCalculator.exception.InvalidFormulaException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CalculatorController.class)
class CalculatorControllerTest {

    @MockBean
    private MassCalculator massCalculator;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void failsWhenFormulaIsInvalid() throws Exception{
        when(massCalculator.startCalculator("zz"))
                .thenThrow(new InvalidFormulaException("Invalid formula!"));

        mockMvc.perform(get("/api/massCalculator")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"formula\":\"zz\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Invalid formula!"));
    }

    @Test
    void failsWhenNoElementPresent() throws Exception{
        mockMvc.perform(get("/api/massCalculator")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Invalid formula!"));
    }

    @Test
    void failsWhenArgumentInvalid ()throws Exception{
        when(massCalculator.startCalculator("zzz"))
                .thenThrow(new InvalidFormulaException("Invalid formula!"));

        mockMvc.perform(get("/api/massCalculator?formula=zzz")
                .contentType(MediaType.APPLICATION_JSON)
                .content(""))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Invalid formula!"));
    }

    @Test
    void failsWhenNoBodyPresent() throws Exception{
        mockMvc.perform(get("/api/massCalculator?formula=")
                .contentType(MediaType.APPLICATION_JSON)
                .content(""))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Invalid formula!"));
    }

    @Test
    void passWhenArgumentPresent() throws Exception{

        List<FormulaDto> formulaDtoList = new ArrayList<>();
        formulaDtoList.add(new FormulaDto("H2O", 18.0152D));

        Map<String, List<FormulaDto>> responseMap = new HashMap<>();
        responseMap.put("formulas", formulaDtoList);


        when(massCalculator.startCalculator("H2O"))
                .thenReturn(responseMap);

        mockMvc.perform(get("/api/massCalculator?formula=H2O")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.formulas[0].formula").value("H2O"))
                .andExpect(jsonPath("$.formulas[0].mass").value(18.0152D));

    }

    @Test
    void passWhenValidFormula() throws Exception {

        List<FormulaDto> formulaDtoList = new ArrayList<>();
        formulaDtoList.add(new FormulaDto("H2O", 18.0152D));

        Map<String, List<FormulaDto>> responseMap = new HashMap<>();
        responseMap.put("formulas", formulaDtoList);


        when(massCalculator.startCalculator("H2O"))
                .thenReturn(responseMap);

        mockMvc.perform(get("/api/massCalculator")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"formula\":\"H2O\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.formulas[0].formula").value("H2O"))
                .andExpect(jsonPath("$.formulas[0].mass").value(18.0152D));
    }

}
