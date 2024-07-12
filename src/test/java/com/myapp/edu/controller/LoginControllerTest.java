package com.myapp.edu.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myapp.edu.common.MemberConst;
import com.myapp.edu.domain.Member;
import com.myapp.edu.enums.Role;
import com.myapp.edu.dto.member.MemberLogin;
import com.myapp.edu.dto.member.MemberSession;
import com.myapp.edu.service.LoginService;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = LoginController.class)
class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private LoginService loginService;

    @Test
    void shouldCreateSession() throws Exception {
        // given
        String testEmail = "test@example.com";
        String testPassword = "TestUser1";

        Member member = new Member("testuser", testEmail, testPassword,
                "010-1234-5678", Role.INSTRUCTOR);
        when(loginService.login(testEmail, testPassword)).thenReturn(member);

        // when
        MvcResult result = mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new MemberLogin(testEmail, "TestUser1"))))
                .andExpect(status().isOk())
                .andReturn();

        // then
        // 세션 확인
        HttpSession session = result.getRequest().getSession(false);
        assertThat(session).isNotNull();

        // 세션 저장 값 확인
        MemberSession memberSession = (MemberSession) session.getAttribute(MemberConst.LOGIN_MEMBER);
        assertThat(memberSession).isNotNull();
        assertThat(memberSession.getEmail()).isEqualTo(testEmail);
        assertThat(memberSession.getRole()).isEqualTo(Role.INSTRUCTOR);
    }

}