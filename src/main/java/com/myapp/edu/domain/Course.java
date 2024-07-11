package com.myapp.edu.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Course extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    private Long id;

    private String title;

    private BigDecimal price;

    private Long maxCapacity;

    private Long currentEnrollment = 0L;

    private Double enrollmentRate = 0.0;

    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY)
    private Set<MemberCourse> memberCourseList = new HashSet<>();

    public Course(String title, BigDecimal price, Long maxCapacity) {
        this.title = title;
        this.price = price;
        this.maxCapacity = maxCapacity;
    }
}
