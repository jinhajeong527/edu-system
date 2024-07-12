package com.myapp.edu.service;

import com.myapp.edu.domain.Course;
import com.myapp.edu.domain.Member;
import com.myapp.edu.domain.MemberCourse;
import com.myapp.edu.dto.course.CourseApply;
import com.myapp.edu.dto.course.CourseResponse;
import com.myapp.edu.enums.Status;
import com.myapp.edu.dto.course.CourseSave;
import com.myapp.edu.repository.CourseRepository;
import com.myapp.edu.repository.MemberCourseRepository;
import com.myapp.edu.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final MemberRepository memberRepository;

    private final CourseRepository courseRepository;

    private final MemberCourseRepository memberCourseRepository;

    @Transactional
    public CourseResponse create(String instructorEmail, CourseSave courseSave) {
        Optional<Member> memberOptional = memberRepository.findByEmail(instructorEmail);
        Member member = memberOptional
                .orElseThrow(() -> new IllegalStateException("No member found"));

        Course savedCourse = courseRepository.save(new Course(courseSave.getTitle(), courseSave.getPrice(), courseSave.getMaxCapacity()));

        memberCourseRepository.save(new MemberCourse(member, savedCourse, Status.TEACHING));

        return CourseResponse.fromEntity(savedCourse, member);
    }

    public Page<CourseResponse> getAllCourses(PageRequest pageRequest) {
        Page<Course> courses = courseRepository.findAllCourses(pageRequest);
        return courses.map(CourseResponse::fromEntity);
    }
    @Transactional
    public List<CourseResponse> applyForCourses(String studentEmail, CourseApply courseApply) {
        Member member = memberRepository.findByEmail(studentEmail)
                .orElseThrow(() -> new IllegalStateException("No member found"));

        List<Course> courses = courseRepository.findAllByIdIn(courseApply.getCourseIds())
                .stream()
                .filter(c -> c.getCurrentEnrollment() < c.getMaxCapacity())
                .map(c -> c.enrollMember(member))
                .toList();
        return courses.stream()
                .map(CourseResponse::fromEntity)
                .collect(Collectors.toList());
    }
}
