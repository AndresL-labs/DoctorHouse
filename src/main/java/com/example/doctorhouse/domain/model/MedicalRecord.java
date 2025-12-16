package com.example.doctorhouse.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MedicalRecord {
    private Long id;
    private Long appointmentId;
    @jakarta.validation.constraints.NotBlank(message = "Diagnosis is required")
    private String diagnosis;

    @jakarta.validation.constraints.NotBlank(message = "Treatment is required")
    private String treatment;

    private String observations;
    private String bloodPressure;
    private Integer heartRate;
    private Double weightKg;
    private LocalTime startedAt;
    private LocalTime finishedAt;
}
