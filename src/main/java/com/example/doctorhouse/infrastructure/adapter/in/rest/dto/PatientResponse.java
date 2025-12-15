package com.example.doctorhouse.infrastructure.adapter.in.rest.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class PatientResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private LocalDateTime createdAt;
}
