package com.adrian99.molecularMassCalculator.calculator.implementation;

import com.adrian99.molecularMassCalculator.calculator.MassCalculator;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class MassCalculatorImpl implements MassCalculator {
    @Override
    public Double calculate(String formula) {
        Map<String, Double> masses = masses();

        Map<String, Integer> integerMap = new HashMap<>();

        for (int i = 0; i < formula.length(); i++) {
            if (isUpper(formula.charAt(i)) && (i == formula.length() - 1)) {
                integerMap.put(String.valueOf(formula.charAt(i)), 1);
                break;
            }

            if (isUpper(formula.charAt(i))) {
                if (isUpper(formula.charAt(i + 1))) {
                    integerMap.put(String.valueOf(formula.charAt(i)), 1);
                    continue;
                }
                if (isNumber(formula.charAt(i + 1))) {
                    if (i + 2 < formula.length())
                        if (isNumber(formula.charAt(i + 2))) {
                            integerMap.put(formula.substring(i, i + 1), Integer.parseInt(formula.substring(i + 1, i + 3)));
                            i += 2;
                            continue;
                        }
                    integerMap.put(String.valueOf(formula.charAt(i)), (formula.charAt(i + 1) - '0'));
                    continue;
                }
                if (isLower(formula.charAt(i + 1))) {
                    if (i + 2 == formula.length()) {
                        integerMap.put(formula.substring(i, i + 2), 1);
                        break;
                    }
                    if (isNumber(formula.charAt(i + 2))) {
                        if (i + 3 < formula.length()) {
                            if (isNumber(formula.charAt(i + 3))) {
                                integerMap.put(formula.substring(i, i + 2), Integer.parseInt(formula.substring(i + 2, i + 4)));
                                i += 3;
                                continue;
                            }
                        }
                        integerMap.put(formula.substring(i, i + 2), (formula.charAt(i + 2) - '0'));
                        i += 2;
                    }
                }
            }
        }
        double mass = 0D;

        for (var entry : integerMap.entrySet()) {

            mass += (entry.getValue() * masses.get(entry.getKey()));
        }

        return mass;
    }

    public Map<String, Double> masses() {
        File file = new File(".\\src\\main\\resources\\data\\MolecularMasses.txt");

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

    public static boolean isLower(char c) {
        return c >= 'a' && c <= 'z';
    }

    public static boolean isUpper(char c) {
        return c >= 'A' && c <= 'Z';
    }

    public static boolean isNumber(char c) {
        return c >= '0' && c <= '9';
    }
}

