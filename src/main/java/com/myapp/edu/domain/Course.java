package com.myapp.edu.domain;

import com.myapp.edu.enums.Status;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
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

    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<MemberCourse> memberCourseList = new HashSet<>();

    public Course(String title, BigDecimal price, Long maxCapacity) {
        this.title = title;
        this.price = price;
        this.maxCapacity = maxCapacity;
    }

    public Course(Long id, String title, BigDecimal price, Long maxCapacity) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.maxCapacity = maxCapacity;
    }

    public Course enrollMember(Member member) {
        this.memberCourseList.add(new MemberCourse(member, this, Status.ENROLLED));
        this.currentEnrollment++;
        this.enrollmentRate = (double) this.currentEnrollment / this.maxCapacity * 100;
        return this;
    }
}
