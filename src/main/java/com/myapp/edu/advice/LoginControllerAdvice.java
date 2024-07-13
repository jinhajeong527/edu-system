package com.myapp.edu.advice;

import com.myapp.edu.dto.rest.RestResponse;
import com.myapp.edu.exception.InvalidCredentialException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class LoginControllerAdvice {

    @ExceptionHandler(InvalidCredentialException.class)
    public ResponseEntity<RestResponse<String>> handleInvalidCredentialsException(InvalidCredentialException e) {
        RestResponse<String> response = new RestResponse<>(e.getMessage(), 400);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
