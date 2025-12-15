package com.example.doctorhouse.infraestructure.adapter.in.dto.request;

import com.example.doctorhouse.domain.model.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RegisterUserRequestDto(
        @NotBlank String firstName,
        @NotBlank String lastName,
        @NotBlank String dni,
        @Email @NotBlank String email,
        String password,
        @NotBlank String phone,
        Role role
) {
}
