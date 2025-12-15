package com.example.doctorhouse.infrastructure.adapter.in.rest.dto;

import com.example.doctorhouse.domain.model.enums.UserRole;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class UserResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private UserRole role;
    private LocalDateTime createdAt;
}
