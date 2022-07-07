package com.adrian99.molecularMassCalculator.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class InvalidFormulaExceptionHandler {
    @ExceptionHandler(value = {InvalidFormulaException.class})
    public ResponseEntity<Object> handleInvalidFormulaException(InvalidFormulaException e){
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("message",e.getMessage());
        return new ResponseEntity<>(responseMap, HttpStatus.BAD_REQUEST);
    }
}
