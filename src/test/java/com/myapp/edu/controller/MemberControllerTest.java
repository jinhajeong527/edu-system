package com.myapp.edu.controller;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.myapp.edu.domain.Member;
import com.myapp.edu.dto.error.ErrorResult;
import com.myapp.edu.dto.rest.RestResponse;
import com.myapp.edu.enums.Role;
import com.myapp.edu.dto.member.MemberJoin;
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


import java.util.List;

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
        RestResponse<List<ErrorResult>> restResponse = objectMapper.readValue(contentAsString, new TypeReference<>(){});
        List<ErrorResult> list = restResponse.getData();
        assertThat(list.size()).isEqualTo(4);
    }

    @Test
    void shouldReturnCreatedForValidInfo() throws Exception {
        // given
        MemberJoin memberJoin = new MemberJoin("testuser", "test33@example.com",
                "TestUser1", "010-1234-5678", Role.INSTRUCTOR);

        Member member = new Member(1L, "testuser", "test33@example.com",
                "TestUser1", "010-1234-5678", Role.INSTRUCTOR);
        MemberResponse memberResponse = MemberResponse.convertToMemberResponse(member);

        when(memberService.join(ArgumentMatchers.any(MemberJoin.class))).thenReturn(memberResponse);

        // when
        MvcResult result = mockMvc.perform(post("/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(memberJoin)))
                        .andExpect(status().isCreated())
                        .andReturn();
        // then
        String contentAsString = result.getResponse().getContentAsString();
        RestResponse<MemberResponse> restResponse = objectMapper.readValue(contentAsString, new TypeReference<>(){});
        MemberResponse response  = restResponse.getData();
        assertThat(response.getId()).isEqualTo(member.getId());
        assertThat(response.getUsername()).isEqualTo(member.getUsername());
        assertThat(response.getEmail()).isEqualTo(member.getEmail());
        assertThat(response.getPhoneNumber()).isEqualTo(member.getPhoneNumber());
        assertThat(response.getRole()).isEqualTo(member.getRole());
    }

}