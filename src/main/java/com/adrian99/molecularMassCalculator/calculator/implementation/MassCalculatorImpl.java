package com.adrian99.molecularMassCalculator.calculator.implementation;

import com.adrian99.molecularMassCalculator.calculator.MassCalculator;
import com.adrian99.molecularMassCalculator.dto.FormulaDto;
import com.adrian99.molecularMassCalculator.exception.InvalidFormulaException;
import org.springframework.stereotype.Component;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Component
public class MassCalculatorImpl implements MassCalculator {

    Map<String, Double> mass = masses();
    List<String> elements = new ArrayList<>();

    @Override
    public Map<String,List<FormulaDto>> startCalculator(String formula) {

        formula = formula.toUpperCase();
        String newFormula = formula.replaceAll("\\d", "");

        splitFormula(newFormula, "");

        List<FormulaDto> map = new ArrayList<>();

        if (elements.size() == 0)
            throw new InvalidFormulaException("Invalid formula!");

        for (String e : elements) {
            StringBuilder currentElement = new StringBuilder();
            double mass = 0D;
            String[] list = e.split(" ");
            for (String el : list) {
                if (!el.isEmpty()) {
                    if (!formula.contains(el.toUpperCase())) {
                        mass = -1;
                        break;
                    }

                    int num = ifNumber(formula.indexOf(el.toUpperCase()) + el.length() - 1, formula);
                    currentElement.append(el).append(num == 1 ? "" : num);
                    mass += num * this.mass.get(el);
                }
            }

            if (mass == -1)
                continue;

            map.add(new FormulaDto(currentElement.toString(),
                    BigDecimal.valueOf(mass).setScale(4, RoundingMode.HALF_EVEN).doubleValue()));
        }

        elements.clear();

        Map<String, List<FormulaDto>> formulasMap = new HashMap<>();
        formulasMap.put("formulas", map);

        return formulasMap;
    }

    public int ifNumber(int position, String formula) {
        Integer number = null;
        if (position == formula.length() - 1)
            return 1;

        try {
            if (position + 2 >= formula.length())
                number = Integer.parseInt(formula.substring(position + 1, position + 2));
            else
                number = Integer.parseInt(formula.substring(position + 1, position + 3));

            return number;

        } catch (NumberFormatException e) {
            try {
                number = Integer.parseInt(formula.substring(position + 1, position + 2));;
                return number;
            } catch (NumberFormatException ex) {
                return 1;
            }
        }
    }

    public void splitFormula(String formula, String currentForm) {
        int length = formula.length();
        if (length <= 1) {
            if (mass.containsKey(formula))
                elements.add(currentForm + " " + formula);
            return;
        }
        String lowerCase = String.valueOf(formula.charAt(1)).toLowerCase();
        if (length == 2) {
            String elem = formula.charAt(0) + lowerCase;
            if (mass.containsKey(elem))
                elements.add(currentForm + " " + elem);
        }

        if (mass.containsKey(formula.substring(0, 1)))
            splitFormula(formula.substring(1, length), currentForm + " " + formula.charAt(0));

        String elem = formula.charAt(0) + lowerCase;
        if (mass.containsKey(elem))
            splitFormula(formula.substring(2, length), currentForm + " " + elem);
    }


    public Map<String, Double> masses() {

        Path currentPath = Paths.get(System.getProperty("user.dir"));
        Path filePath = Paths.get(currentPath.toString(), "src", "main","resources","data","MolecularMasses.txt");

        File file = new File(filePath.toString());

        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        String st = null;
        Map<String, Double> map = new HashMap<>();
        while (true) {
            try {
                if (!((st = br.readLine()) != null)) break;
            } catch (IOException e) {
                e.printStackTrace();
            }
            String[] elems = st.split("\t");
            map.put(elems[1], Double.parseDouble(elems[0]));
        }

        return map;
    }
}

