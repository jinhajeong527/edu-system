package com.myapp.edu.service;

import com.myapp.edu.domain.Member;
import com.myapp.edu.domain.enums.Role;
import com.myapp.edu.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
        Member member = new Member("testuser", "testuser@example.com",
                "password123", "010-1234-5678", Role.INSTRUCTOR);
        // stub
        given(memberRepository.save(any(Member.class))).willReturn(member);

        // when
        Member savedMember = memberService.join(member);

        // then
        assertThat(savedMember).isNotNull();
        assertThat(savedMember.getUsername()).isEqualTo("testuser");
        assertThat(savedMember.getEmail()).isEqualTo("testuser@example.com");
        assertThat(savedMember.getPassword()).isEqualTo("password123");
        assertThat(savedMember.getPhoneNumber()).isEqualTo("010-1234-5678");
        assertThat(savedMember.getRole()).isEqualTo(Role.INSTRUCTOR);

        verify(memberRepository).save(member);
    }

}