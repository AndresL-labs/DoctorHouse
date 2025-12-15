package com.example.doctorhouse.infraestructure.adapter.in.dto.request;

import jakarta.validation.constraints.NotBlank;

public record ChangePasswordRequestDto(
        @NotBlank String email,
        @NotBlank String oldPassword,
        @NotBlank String newPassword
) {
}
