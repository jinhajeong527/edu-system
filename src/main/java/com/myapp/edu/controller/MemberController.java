package com.myapp.edu.controller;

import com.myapp.edu.domain.Member;
import com.myapp.edu.dto.member.MemberJoin;
import com.myapp.edu.dto.member.MemberResponse;
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
    public ResponseEntity<MemberResponse> join(@Validated @RequestBody MemberJoin memberJoinDto) {
        Member member = memberService.join(convertToMemberEntity(memberJoinDto));
        return new ResponseEntity<>(convertToMemberDto(member), HttpStatus.CREATED);
    }

    private Member convertToMemberEntity(MemberJoin memberJoinDto) {
        return new Member(
                memberJoinDto.getUsername(),
                memberJoinDto.getEmail(),
                memberJoinDto.getPassword(),
                memberJoinDto.getPhoneNumber(),
                memberJoinDto.getRole()
        );
    }
    private MemberResponse convertToMemberDto(Member member) {
        return new MemberResponse(member.getId(), member.getUsername(), member.getEmail(), member.getPhoneNumber(), member.getRole());
    }
}






