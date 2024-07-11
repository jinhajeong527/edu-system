package com.myapp.edu.controller;

import com.myapp.edu.annotation.Instructor;
import com.myapp.edu.domain.Course;
import com.myapp.edu.dto.course.CourseResponse;
import com.myapp.edu.dto.course.CourseSave;
import com.myapp.edu.dto.member.MemberSession;
import com.myapp.edu.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @PostMapping
    public ResponseEntity<?> create(@Instructor MemberSession memberSession, @Validated @RequestBody CourseSave courseSave) {
        if (memberSession == null) {
            return new ResponseEntity<>("강사 회원만 강의를 등록할 수 있습니다.", HttpStatus.UNAUTHORIZED);
        }
        Course course = courseService.create(memberSession.getEmail(), courseSave);
        return new ResponseEntity<CourseResponse>
                (new CourseResponse(course.getId(), course.getTitle(), course.getPrice(),
                        course.getMaxCapacity(), course.getCurrentEnrollment(), course.getEnrollmentRate()), HttpStatus.CREATED);
    }
}
