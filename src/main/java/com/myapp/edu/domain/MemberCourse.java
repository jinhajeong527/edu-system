package com.myapp.edu.domain;

import com.myapp.edu.domain.enums.Status;
import jakarta.persistence.*;

@Entity
public class MemberCourse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;
    @Enumerated(EnumType.STRING)
    private Status status;

}
