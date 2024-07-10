package com.myapp.edu.advice;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.myapp.edu.dto.error.ErrorResponse;
import com.myapp.edu.dto.error.ErrorResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        List<ErrorResult> errors = bindingResult.getFieldErrors()
                .stream()
                .map(fieldError -> new ErrorResult(
                        fieldError.getField(),
                        fieldError.getRejectedValue() != null ? fieldError.getRejectedValue().toString() : "",
                        fieldError.getDefaultMessage())
                )
                .collect(Collectors.toList());

        return new ResponseEntity<>(new ErrorResponse(errors), HttpStatus.BAD_REQUEST);
    }

}
