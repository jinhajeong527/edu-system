package com.myapp.edu.advice;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.myapp.edu.dto.request.response.ErrorResponse;
import com.myapp.edu.dto.request.response.ErrorResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class MemberControllerAdvice {
    @ExceptionHandler
    public ResponseEntity<?> handle(HttpMessageNotReadableException e) {
        if (e.getCause() instanceof InvalidFormatException ex) {
            String fieldName = ex.getPath().get(0).getFieldName();
            String rejectedValue = ex.getValue() != null ? ex.getValue().toString() : "";
            ErrorResult errorResult = new ErrorResult(fieldName, rejectedValue, "Invalid " + fieldName);
            return new ResponseEntity<ErrorResponse>(new ErrorResponse(List.of(errorResult)), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}
