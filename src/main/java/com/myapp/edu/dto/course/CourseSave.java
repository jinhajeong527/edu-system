package com.myapp.edu.dto.course;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CourseSave {

    @NotBlank(message = "Course name must not be blank")
    private String title;

    @NotNull
    @Min(value = 1, message = "Max capacity must be at least 1")
    private Long maxCapacity;

    @NotNull
    @DecimalMin(value = "0", message = "Price must be at least 0")
    private BigDecimal price;
}
