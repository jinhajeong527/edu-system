package com.myapp.edu.advice;

import com.myapp.edu.dto.rest.RestResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;



@RestControllerAdvice
public class CourseControllerAdvice {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<RestResponse<String>> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        RestResponse<String> restResponse = new RestResponse<>(ex.getMessage(), 400);
        return new ResponseEntity<>(restResponse, HttpStatus.BAD_REQUEST);
    }
}
