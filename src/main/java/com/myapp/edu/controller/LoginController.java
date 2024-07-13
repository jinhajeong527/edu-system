package com.myapp.edu.controller;

import com.myapp.edu.common.MemberConst;
import com.myapp.edu.domain.Member;
import com.myapp.edu.dto.member.MemberLogin;
import com.myapp.edu.dto.member.MemberSession;
import com.myapp.edu.dto.rest.RestResponse;
import com.myapp.edu.service.LoginService;
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
@RequestMapping("/auth")
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Validated @RequestBody MemberLogin memberLoginDto, HttpServletRequest request) {
        Member member = loginService.login(memberLoginDto.getEmail(), memberLoginDto.getPassword());

        HttpSession session = request.getSession();
        session.setAttribute(MemberConst.LOGIN_MEMBER, new MemberSession(member.getEmail(), member.getRole()));

        return new ResponseEntity<>(new RestResponse<>("성공적으로 로그인 되었습니다", 200), HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return new ResponseEntity<>(new RestResponse<>("성공적으로 로그아웃 되었습니다", 200), HttpStatus.OK);
    }

}
