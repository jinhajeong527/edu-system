package com.myapp.edu.controller;

import com.myapp.edu.dto.member.MemberJoin;
import com.myapp.edu.dto.member.MemberResponse;
import com.myapp.edu.dto.rest.RestResponse;
import com.myapp.edu.service.MemberService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<RestResponse<MemberResponse>> join(@Validated @RequestBody MemberJoin memberJoinDto) {
        MemberResponse member = memberService.join(memberJoinDto);
        RestResponse<MemberResponse> restResponse = new RestResponse<>("회원가입에 성공하였습니다.", 201, member);
        return new ResponseEntity<>(restResponse, HttpStatus.CREATED);
    }
}






