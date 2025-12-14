package com.example.doctorhouse.infraestructure.adapter.out.persistence.entity.dto;

import java.time.LocalDateTime;

public class ErrorResponseDTO {

    private String message;
    private LocalDateTime timestamp;

    public ErrorResponseDTO() {
    }

    public ErrorResponseDTO(String message) {
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
