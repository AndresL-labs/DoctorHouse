package com.example.doctorhouse.infraestructure.adapter.in.dto.response;

public record LoginUserResponseDto(
        String token,
        boolean firstLogin
) {
}
