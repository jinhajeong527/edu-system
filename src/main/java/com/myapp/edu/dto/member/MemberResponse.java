package com.myapp.edu.dto.member;

import com.myapp.edu.domain.Member;
import com.myapp.edu.enums.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class MemberResponse {
    private Long id;
    private String username;
    private String email;
    private String phoneNumber;
    private Role role;

    public MemberResponse(Long id, String username, String email, String phoneNumber, Role role) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }

    public static MemberResponse convertToMemberResponse(Member member) {
        return new MemberResponse(member.getId(), member.getUsername(), member.getEmail(), member.getPhoneNumber(), member.getRole());
    }
}
