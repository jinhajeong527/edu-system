package com.myapp.edu.service;

import com.myapp.edu.domain.Member;
import com.myapp.edu.exception.InvalidCredentialException;
import com.myapp.edu.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final MemberRepository memberRepository;

    public Member login(String email, String password) {
        return memberRepository.findByEmailAndPassword(email, password)
                .orElseThrow(() -> new InvalidCredentialException("아이디 혹은 비밀번호를 잘못 입력하였습니다."));
    }

}
