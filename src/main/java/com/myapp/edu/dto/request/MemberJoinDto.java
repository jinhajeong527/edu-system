package com.myapp.edu.dto.request;

import com.myapp.edu.domain.enums.Role;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MemberJoinDto {
    @NotBlank(message = "Username is required")
    private String username;

    @Size(min = 6, max = 10, message = "Password must be between 6 and 10 characters")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$|^(?=.*[a-z])(?=.*[A-Z]).*$|^(?=.*[a-z])(?=.*\\d).*$|^(?=.*[A-Z])(?=.*\\d).*$",
            message = "Password must contain at least two of the following: lowercase letters, uppercase letters, numbers"
    )
    @NotBlank(message = "Password is required")
    private String password;

    @Pattern(
            regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$",
            message = "Email should be valid"
    )
    @NotBlank(message = "Email is required")
    private String email;

    @Pattern(
            regexp = "^(010|011|016|017|018|019)-\\d{3,4}-\\d{4}$",
            message = "Phone number must be in the format xxx-xxxx-xxxx or xxx-xxx-xxxx"
    )
    private String phoneNumber;

    @NotNull(message = "Role is required")
    private Role role;
}
