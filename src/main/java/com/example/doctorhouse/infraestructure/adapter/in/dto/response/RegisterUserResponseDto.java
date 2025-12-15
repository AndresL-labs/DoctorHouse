package com.example.doctorhouse.infraestructure.adapter.in.dto.response;

public record RegisterUserResponseDto (
        String email,
        boolean firstLogin,
        String message) {
}
