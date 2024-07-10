package com.myapp.edu.dto.response;

import com.myapp.edu.domain.enums.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class MemberDto {
    private Long id;
    private String username;
    private String email;
    private String phoneNumber;
    private Role role;

    public MemberDto(Long id, String username, String email, String phoneNumber, Role role) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }
}
