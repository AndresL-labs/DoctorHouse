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

    @jakarta.validation.constraints.NotBlank(message = "Observations are required")
    private String observations;

    @jakarta.validation.constraints.NotBlank(message = "Blood Pressure is required")
    private String bloodPressure;

    @jakarta.validation.constraints.NotNull(message = "Heart Rate is required")
    @jakarta.validation.constraints.Min(value = 1, message = "Heart Rate must be greater than 0")
    private Integer heartRate;

    @jakarta.validation.constraints.NotNull(message = "Weight is required")
    @jakarta.validation.constraints.DecimalMin(value = "0.1", message = "Weight must be greater than 0")
    @jakarta.validation.constraints.DecimalMax(value = "250.0", message = "Weight cannot exceed 250 kg")
    private Double weightKg;

    @jakarta.validation.constraints.NotNull(message = "Start Time is required")
    private LocalTime startedAt;

    @jakarta.validation.constraints.NotNull(message = "Finish Time is required")
    private LocalTime finishedAt;

    @jakarta.validation.constraints.AssertTrue(message = "Start time must be before finish time")
    public boolean isTimeRangeValid() {
        if (startedAt == null || finishedAt == null) {
            return true; // Let NotNull handle nulls
        }
        return startedAt.isBefore(finishedAt);
    }
}
