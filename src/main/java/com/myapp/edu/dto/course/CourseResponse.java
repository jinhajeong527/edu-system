package com.myapp.edu.dto.course;

import com.myapp.edu.domain.Course;
import com.myapp.edu.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

@Data
@AllArgsConstructor
public class CourseResponse {

    private Long id;

    private String title;

    private BigDecimal price;

    private Long maxCapacity;

    private Long currentEnrollment;

    private List<String> instructors;

    public static CourseResponse fromEntity(Course course, Member member) {
        return new CourseResponse(course.getId(), course.getTitle(), course.getPrice(),
                course.getMaxCapacity(), course.getCurrentEnrollment(), Collections.singletonList(member.getUsername()));
    }

}
