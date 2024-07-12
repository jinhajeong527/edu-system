package com.myapp.edu.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myapp.edu.domain.Member;
import com.myapp.edu.enums.Role;
import com.myapp.edu.dto.member.MemberJoin;
import com.myapp.edu.dto.error.ErrorResponse;
import com.myapp.edu.dto.member.MemberResponse;
import com.myapp.edu.service.MemberService;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
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

@WebMvcTest(controllers = MemberController.class)
class MemberControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MemberService memberService;
    @Test
    void shouldReturnBadRequestForInvalidInfo() throws Exception {
        // given
        MemberJoin memberJoinDto = new MemberJoin(
                "jinha",
                "jinha@test", // invalid
                "1111", // invalid -> 길이, 형식 두가지
                "200-333-4444", // invalid
                Role.STUDENT);
        // when
        MvcResult result = mockMvc.perform(post("/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(memberJoinDto)))
                        .andExpect(status().isBadRequest())
                        .andReturn();
        // then
        String contentAsString = result.getResponse().getContentAsString();
        ErrorResponse errorResponse = objectMapper.readValue(contentAsString, ErrorResponse.class);
        assertThat(errorResponse.getErrors().size()).isEqualTo(4);
    }

    @Test
    void shouldReturnCreatedForValidInfo() throws Exception {
        // given
        MemberJoin memberJoinDto = new MemberJoin(
                "testuser", "test33@example.com", "TestUser2",
                "010-1234-5678", Role.INSTRUCTOR
        );

        Member member = new Member(
                "testuser", "test33@example.com", "TestUser2",
                "010-1234-5678", Role.INSTRUCTOR
        );

        when(memberService.join(ArgumentMatchers.any(Member.class))).thenReturn(member);
        // when
        MvcResult result = mockMvc.perform(post("/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(memberJoinDto)))
                        .andExpect(status().isCreated())
                        .andReturn();
        // then
        String contentAsString = result.getResponse().getContentAsString();
        MemberResponse response = objectMapper.readValue(contentAsString, MemberResponse.class);

        assertThat(response.getUsername()).isEqualTo(member.getUsername());
        assertThat(response.getEmail()).isEqualTo(member.getEmail());
        assertThat(response.getPhoneNumber()).isEqualTo(member.getPhoneNumber());
        assertThat(response.getRole()).isEqualTo(member.getRole());
    }

}