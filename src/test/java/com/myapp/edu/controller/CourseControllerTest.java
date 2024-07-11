package com.myapp.edu.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myapp.edu.common.MemberConst;
import com.myapp.edu.domain.Course;
import com.myapp.edu.domain.enums.Role;
import com.myapp.edu.dto.course.CourseSave;
import com.myapp.edu.dto.member.MemberSession;
import com.myapp.edu.service.CourseService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CourseController.class)
class CourseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CourseService courseService;

    @Test
    void shouldFailToCreateCourse() throws Exception {
        MemberSession session = new MemberSession("test@example.com", Role.STUDENT);
        CourseSave courseSave = new CourseSave("course1", 5L, new BigDecimal(100000));

        mockMvc.perform(post("/courses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(courseSave))
                        .sessionAttr(MemberConst.LOGIN_MEMBER, session))
                        .andExpect(status().isUnauthorized());
    }

    @Test
    void shouldCreateCourse() throws Exception {
        MemberSession session = new MemberSession("test@example.com", Role.INSTRUCTOR);
        CourseSave courseSave = new CourseSave("course1", 5L, new BigDecimal(100000));
        Course course = new Course("course1", new BigDecimal(100000), 5L);

        given(courseService.create(
                ArgumentMatchers.anyString(), ArgumentMatchers.any(CourseSave.class))).willReturn(course);

        // when
        mockMvc.perform(post("/courses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(courseSave))
                        .sessionAttr(MemberConst.LOGIN_MEMBER, session))
                .andExpect(status().isCreated());
    }

}