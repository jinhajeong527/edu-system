package com.myapp.edu.dto.error;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResult {
    private String field;
    private String rejectedValue;
    private String message;
}
