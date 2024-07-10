package com.myapp.edu.controller;

import com.myapp.edu.common.MemberConst;
import com.myapp.edu.domain.Member;
import com.myapp.edu.dto.member.MemberJoin;
import com.myapp.edu.dto.member.MemberLogin;
import com.myapp.edu.dto.member.MemberResponse;
import com.myapp.edu.dto.member.MemberSession;
import com.myapp.edu.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/join")
    public ResponseEntity<MemberResponse> join(@Validated @RequestBody MemberJoin memberJoinDto) {
        Member member = memberService.join(convertToMemberEntity(memberJoinDto));
        return new ResponseEntity<>(convertToMemberDto(member), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Validated @RequestBody MemberLogin memberLoginDto, HttpServletRequest request) {
        Member member = memberService.login(memberLoginDto.getEmail(), memberLoginDto.getPassword());

        HttpSession session = request.getSession();
        session.setAttribute(MemberConst.LOGIN_MEMBER, new MemberSession(member.getEmail(), member.getRole()));

        return new ResponseEntity<>("ok", HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return new ResponseEntity<>("ok", HttpStatus.OK);
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
