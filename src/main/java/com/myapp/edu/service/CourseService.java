package com.myapp.edu.service;

import com.myapp.edu.domain.Course;
import com.myapp.edu.domain.Member;
import com.myapp.edu.domain.MemberCourse;
import com.myapp.edu.domain.enums.Status;
import com.myapp.edu.dto.course.CourseSave;
import com.myapp.edu.repository.CourseRepository;
import com.myapp.edu.repository.MemberCourseRepository;
import com.myapp.edu.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final MemberRepository memberRepository;

    private final CourseRepository courseRepository;

    private final MemberCourseRepository memberCourseRepository;

    @Transactional
    public Course create(String instructorEmail, CourseSave courseSave) {
        Optional<Member> memberOptional = memberRepository.findByEmail(instructorEmail);
        Member member = memberOptional
                .orElseThrow(() -> new IllegalStateException("No member found"));

        Course savedCourse = courseRepository.save(new Course(courseSave.getTitle(), courseSave.getPrice(), courseSave.getMaxCapacity()));

        memberCourseRepository.save(new MemberCourse(member, savedCourse, Status.TEACHING));
        return savedCourse;
    }

}
