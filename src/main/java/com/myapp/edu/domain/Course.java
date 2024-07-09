package com.myapp.edu.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    private Long id;

    private String title;

    private BigDecimal price;

    private Long maxCapacity;

    private Long currentEnrollment;

    private Double enrollmentRate;

    @OneToMany(mappedBy = "course")
    private Set<MemberCourse> memberCourseList = new HashSet<>();

}
