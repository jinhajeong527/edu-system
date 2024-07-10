package com.myapp.edu.dto.request.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
    private List<ErrorResult> errors;
}
