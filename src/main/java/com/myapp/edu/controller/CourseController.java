package com.myapp.edu.controller;

import com.myapp.edu.annotation.Instructor;
import com.myapp.edu.common.MemberConst;
import com.myapp.edu.dto.course.CourseApply;
import com.myapp.edu.dto.course.CourseResponse;
import com.myapp.edu.dto.course.CourseSave;
import com.myapp.edu.dto.member.MemberSession;
import com.myapp.edu.dto.rest.RestResponse;
import com.myapp.edu.enums.SortOption;
import com.myapp.edu.service.CourseService;
import lombok.RequiredArgsConstructor;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @PostMapping
    public ResponseEntity<?> create(@Instructor MemberSession memberSession, @Validated @RequestBody CourseSave courseSave) {
        if (memberSession == null) {
            return new ResponseEntity<>(new RestResponse<>("강사 회원만 강의를 등록할 수 있습니다.", 403), HttpStatus.FORBIDDEN);
        }
        CourseResponse course = courseService.create(memberSession.getEmail(), courseSave);
        return new ResponseEntity<>(new RestResponse<>("강의가 성공적으로 등록 되었습니다", 201, course), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> getAllCourses(@RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "20") int size,
                                           @RequestParam(defaultValue = "RECENTLY_ADDED") SortOption sortOption,
                                           @RequestParam(defaultValue = "DESC") Sort.Direction direction) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(direction, sortOption.getField()));
        Page<CourseResponse> courses = courseService.getAllCourses(pageRequest);
        return new ResponseEntity<>(new RestResponse<>("강의가 성공적으로 조회 되었습니다", 200, courses), HttpStatus.OK);
    }

    @PostMapping("/apply")
    public ResponseEntity<?> applyForCourses(
            @SessionAttribute(name = MemberConst.LOGIN_MEMBER, required = false) MemberSession memberSession,
            @Validated @RequestBody CourseApply courseApply) {
        try {
            List<CourseResponse> courses = courseService.applyForCourses(memberSession.getEmail(), courseApply);
            return new ResponseEntity<>(new RestResponse<>("수강 신청에 성공한 강의 목록입니다.", 200, courses), HttpStatus.OK);
        } catch(DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("이미 수강신청 완료된 강의입니다.");
        }
    }

}
