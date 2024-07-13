package com.myapp.edu.service;

import com.myapp.edu.domain.Member;
import com.myapp.edu.dto.member.MemberJoin;
import com.myapp.edu.dto.member.MemberResponse;
import com.myapp.edu.enums.Role;
import com.myapp.edu.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {
    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private MemberService memberService;

    @Test
    void shouldSaveMember() {
        // given
        MemberJoin memberJoin = new MemberJoin("testuser", "testuser@example.com",
                "password123", "010-1234-5678", Role.INSTRUCTOR);
        Member member = new Member(1L, "testuser", "testuser@example.com",
                "password123", "010-1234-5678", Role.INSTRUCTOR);
        given(memberRepository.save(any(Member.class))).willReturn(member); // 저장될 Member 객체 설정

        // when
        MemberResponse savedMember = memberService.join(memberJoin);

        // then
        // ArgumentCaptor를 사용하여 memberRepository.save 메서드 호출 시 전달된 Member 객체 캡처
        ArgumentCaptor<Member> memberCaptor = ArgumentCaptor.forClass(Member.class);
        verify(memberRepository).save(memberCaptor.capture());

        // 캡처된 Member 객체가 예상된 속성값을 가지고 있는지 확인
        Member capturedMember = memberCaptor.getValue();
        assertThat(capturedMember.getUsername()).isEqualTo("testuser");
        assertThat(capturedMember.getEmail()).isEqualTo("testuser@example.com");
        assertThat(capturedMember.getPhoneNumber()).isEqualTo("010-1234-5678");
        assertThat(capturedMember.getRole()).isEqualTo(Role.INSTRUCTOR);

        assertThat(savedMember).isNotNull();
        assertThat(savedMember.getUsername()).isEqualTo("testuser");
        assertThat(savedMember.getEmail()).isEqualTo("testuser@example.com");
        assertThat(savedMember.getPhoneNumber()).isEqualTo("010-1234-5678");
        assertThat(savedMember.getRole()).isEqualTo(Role.INSTRUCTOR);

    }

}