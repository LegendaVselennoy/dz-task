package com.demo.exception.advice;

import com.demo.exception.NoEditAccessException;
import com.demo.exception.ResourceNotFondException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFondException.class)
    public ResponseEntity<String> deleteNot(ResourceNotFondException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(NoEditAccessException.class)
    public ResponseEntity<String> noEditAccess(NoEditAccessException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<List<Map<String, String>>> handleBindErrors(MethodArgumentNotValidException exception) {
        List<Map<String, String>> errorList = exception.getFieldErrors()
                .stream()
                .map(fieldError -> {
                    Map<String, String> errorMap = new HashMap<>();
                    errorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
                    return errorMap;
                }).toList();
        return ResponseEntity.badRequest().body(errorList);
    }
}