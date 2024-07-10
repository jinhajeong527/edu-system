package com.myapp.edu.dto.request.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResult {
    private String field;
    private String rejectedValue;
    private String message;
}
