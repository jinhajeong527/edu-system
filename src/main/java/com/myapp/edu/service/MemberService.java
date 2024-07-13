package com.myapp.edu.service;

import com.myapp.edu.domain.Member;
import com.myapp.edu.dto.member.MemberJoin;
import com.myapp.edu.dto.member.MemberResponse;
import com.myapp.edu.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberResponse join(MemberJoin memberJoin) {
        Member member = memberRepository.save(memberJoin.convertToMember());
        return MemberResponse.convertToMemberResponse(member);
    }
}
