package com.myapp.edu.dto.course;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
public class CourseApply {
    @NotEmpty(message = "Course IDs cannot be empty")
    private Set<Long> courseIds;

    public CourseApply(Set<Long> courseIds) {
        this.courseIds = courseIds;
    }
}
