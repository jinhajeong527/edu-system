package com.myapp.edu.repository;

import com.myapp.edu.domain.Member;
import com.myapp.edu.domain.enums.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
@DataJpaTest
@Transactional
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    void shouldSaveAndRetrieveMember() {
        // given
        Member member = new Member(
                "test",
                "test@gmail.com",
                "pass2word1",
                "010-4234-3434",
                Role.STUDENT);
        // when
        Member savedMember = memberRepository.save(member);
        // then
        assertThat(savedMember).isSameAs(member);
        assertThat(savedMember.getId()).isEqualTo(member.getId());
        assertThat(savedMember.getEmail()).isEqualTo(member.getEmail());
        assertThat(savedMember.getPassword()).isEqualTo(member.getPassword());
        assertThat(savedMember.getPhoneNumber()).isEqualTo(member.getPhoneNumber());
        assertThat(savedMember.getRole()).isEqualTo(member.getRole());
    }

}