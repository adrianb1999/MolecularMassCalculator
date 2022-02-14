package com.adrian99.molecularMassCalculator.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

@ControllerAdvice
public class InvalidFormulaExceptionHandler {
    @ExceptionHandler(value = {InvalidFormulaException.class})
    public ResponseEntity<Object> handleInvalidFormulaException(InvalidFormulaException e){

        return new ResponseEntity<>(Map.of("message",e.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
