package com.myapp.edu.service;

import com.myapp.edu.domain.Course;
import com.myapp.edu.domain.Member;
import com.myapp.edu.domain.MemberCourse;
import com.myapp.edu.domain.enums.Role;
import com.myapp.edu.dto.course.CourseSave;
import com.myapp.edu.repository.CourseRepository;
import com.myapp.edu.repository.MemberCourseRepository;
import com.myapp.edu.repository.MemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class CourseServiceTest {

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private MemberCourseRepository memberCourseRepository;

    @InjectMocks
    private CourseService courseService;

    @Test
    void shouldThrowIllegalStateExceptionWhenMemberNotFound() {
        // given
        String instructorEmail = "testuser@example.com";
        CourseSave courseSave = new CourseSave("course1", 5L, new BigDecimal(100000));
        given(memberRepository.findByEmail(instructorEmail)).willReturn(Optional.empty());

        // when & then
        Assertions.assertThrows(IllegalStateException.class, () -> courseService.create(instructorEmail, courseSave));
    }

    @Test
    void shouldCreateCourse() {
        // given
        String instructorEmail = "testuser@example.com";
        Member member = new Member("testuser", instructorEmail,
                "password123", "010-1234-5678", Role.INSTRUCTOR);
        CourseSave courseSave = new CourseSave("course1", 5L, new BigDecimal(100000));
        Course course = new Course(courseSave.getTitle(), courseSave.getPrice(), courseSave.getMaxCapacity());

        given(memberRepository.findByEmail(instructorEmail)).willReturn(Optional.of(member));
        given(courseRepository.save(ArgumentMatchers.any(Course.class))).willReturn(course);

        // when
        Course savedCourse = courseService.create(instructorEmail, courseSave);

        // then
        assertThat(savedCourse).isNotNull();
        assertThat(savedCourse.getTitle()).isEqualTo(courseSave.getTitle());
        assertThat(savedCourse.getPrice()).isEqualTo(courseSave.getPrice());
        assertThat(savedCourse.getMaxCapacity()).isEqualTo(courseSave.getMaxCapacity());

        verify(memberRepository).findByEmail(instructorEmail);
        verify(courseRepository).save(ArgumentMatchers.any(Course.class));
        verify(memberCourseRepository).save(ArgumentMatchers.any(MemberCourse.class));
    }



}