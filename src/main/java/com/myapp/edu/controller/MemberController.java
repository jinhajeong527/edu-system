package com.myapp.edu.controller;

import com.myapp.edu.dto.request.MemberJoinDto;
import com.myapp.edu.dto.request.response.ErrorResult;
import com.myapp.edu.dto.request.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/members")
public class MemberController {
    @PostMapping
    public ResponseEntity<?> join(@Validated @RequestBody MemberJoinDto memberJoinDto, BindingResult bindingResult) {
        // 유효성 검사 실패 처리
        if (bindingResult.hasErrors()) {
            List<ErrorResult> errors = bindingResult.getFieldErrors()
                    .stream()
                    .map(fieldError -> new ErrorResult(
                            fieldError.getField(),
                            fieldError.getRejectedValue() != null ? fieldError.getRejectedValue().toString() : "",
                            fieldError.getDefaultMessage())
                    )
                    .toList();

            return new ResponseEntity<ErrorResponse>(new ErrorResponse(errors), HttpStatus.BAD_REQUEST);
        }
        // 회원 가입 로직 처리
        return new ResponseEntity<String>("ok", HttpStatus.CREATED);
    }
}
