package com.myapp.edu.dto.member;

import com.myapp.edu.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberSession {

    private String email;

    private Role role;
}
