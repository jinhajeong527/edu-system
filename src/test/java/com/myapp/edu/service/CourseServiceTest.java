package com.myapp.edu.service;

import com.myapp.edu.domain.Course;
import com.myapp.edu.domain.Member;
import com.myapp.edu.domain.MemberCourse;
import com.myapp.edu.dto.course.CourseApply;
import com.myapp.edu.dto.course.CourseResponse;
import com.myapp.edu.enums.Role;
import com.myapp.edu.dto.course.CourseSave;
import com.myapp.edu.repository.CourseRepository;
import com.myapp.edu.repository.MemberCourseRepository;
import com.myapp.edu.repository.MemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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
        verify(memberRepository).findByEmail(instructorEmail);
    }

    @Test
    void shouldCreateCourse() {
        // given
        String instructorEmail = "testuser@example.com";
        Member member = new Member(1L, "testuser", instructorEmail,
                "password123", "010-1234-5678", Role.INSTRUCTOR);

        CourseSave courseSave = new CourseSave("course1", 5L, new BigDecimal(100000));

        Course course = new Course(1L, courseSave.getTitle(), courseSave.getPrice(), courseSave.getMaxCapacity());

        given(memberRepository.findByEmail(instructorEmail)).willReturn(Optional.of(member));
        given(courseRepository.save(ArgumentMatchers.any(Course.class))).willReturn(course);

        // when
        CourseResponse savedCourse = courseService.create(instructorEmail, courseSave);

        // then
        assertThat(savedCourse).isNotNull();
        assertThat(savedCourse.getTitle()).isEqualTo(courseSave.getTitle());
        assertThat(savedCourse.getPrice()).isEqualTo(courseSave.getPrice());
        assertThat(savedCourse.getMaxCapacity()).isEqualTo(courseSave.getMaxCapacity());

        verify(memberRepository).findByEmail(instructorEmail);
        verify(courseRepository).save(ArgumentMatchers.any(Course.class));
        verify(memberCourseRepository).save(ArgumentMatchers.any(MemberCourse.class));
    }

    @Test
    @DisplayName("정원 초과한 강의는 수강 신청 강의 목록에 미포함 된다.")
    void shouldEnrollMemberInAvailableCourses() {
        // given
        Member member1 = new Member(1L, "testuser1", "testuser1@example.com",
                "password1", "010-1111-5678", Role.STUDENT);
        Course course1 = new Course(1L, "course1", new BigDecimal(100000), 1L);

        course1.enrollMember(member1); // course1에 수강생 1명 증가

        Member member2 = new Member(2L, "testuser2", "testuser2@example.com",
                "password2", "010-2222-5678", Role.STUDENT);
        Course course2 = new Course(2L, "course2", new BigDecimal(200000), 2L);
        course2.enrollMember(member2); // course2에 수강생 1명 증가


        String email3 = "testuser3@example.com";
        Member member3 = new Member(3L, "testuser3", "testuser3@example.com",
                "password3", "010-3333-5678", Role.STUDENT);

        CourseApply courseApply = new CourseApply(Set.of(course1.getId(), course2.getId()));

        given(memberRepository.findByEmail(email3)).willReturn(Optional.of(member3));
        given(courseRepository.findAllByIdIn(courseApply.getCourseIds())).willReturn(List.of(course1, course2));

        // when
        List<CourseResponse> courses = courseService.applyForCourses(email3, courseApply);

        // then
        assertThat(courses).hasSize(1);
        assertThat(courses.get(0).getCurrentEnrollment()).isEqualTo(2);
        assertThat(courses.get(0).getTitle()).isEqualTo("course2");

        verify(memberRepository).findByEmail(email3);
        verify(courseRepository).findAllByIdIn(courseApply.getCourseIds());
    }
}