package com.myapp.edu.dto.course;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
@Data
@AllArgsConstructor
public class CourseResponse {

    private Long id;

    private String title;

    private BigDecimal price;

    private Long maxCapacity;

    private Long currentEnrollment;

    private Double enrollmentRate;
}
