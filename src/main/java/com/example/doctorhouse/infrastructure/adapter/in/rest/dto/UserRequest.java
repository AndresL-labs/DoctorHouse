package com.example.doctorhouse.infrastructure.adapter.in.rest.dto;

import com.example.doctorhouse.domain.model.enums.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserRequest {
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank
    private String dni;
    @NotBlank
    @Email
    private String email;
    private String phone;
    @NotBlank
    private String password;

    @NotNull
    private UserRole role;
}
