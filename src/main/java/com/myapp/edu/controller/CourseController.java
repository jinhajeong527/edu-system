package com.myapp.edu.controller;

import com.myapp.edu.annotation.Instructor;
import com.myapp.edu.dto.course.CourseResponse;
import com.myapp.edu.dto.course.CourseSave;
import com.myapp.edu.dto.member.MemberSession;
import com.myapp.edu.enums.SortOption;
import com.myapp.edu.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
        CourseResponse course = courseService.create(memberSession.getEmail(), courseSave);
        return new ResponseEntity<>(course, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> getAllCourses(@RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "20") int size,
                                           @RequestParam(defaultValue = "RECENTLY_ADDED") SortOption sortOption,
                                           @RequestParam(defaultValue = "DESC") Sort.Direction direction) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(direction, sortOption.getField()));
        Page<CourseResponse> courseResponse = courseService.getAllCourses(pageRequest);
        return new ResponseEntity<>(courseResponse, HttpStatus.OK);
    }

}
