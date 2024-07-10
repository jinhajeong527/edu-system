package com.myapp.edu.dto.member;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MemberLogin {

    @NotEmpty
    private String email;

    @NotEmpty
    private String password;
}
