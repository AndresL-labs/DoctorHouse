package com.example.doctorhouse.domain.model;

import com.example.doctorhouse.domain.model.enums.AppointmentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentWithPatient {
    private Long id;
    private LocalDateTime scheduledAt;
    private Integer duration;
    private AppointmentStatus status;

    // Patient Details
    private String patientName;
    private String patientLastName;
    private LocalDate patientBirthDate;
    private String patientAllergies;
    private String patientBloodType;
    private String patientAddress;
    private String patientPhone;
}
