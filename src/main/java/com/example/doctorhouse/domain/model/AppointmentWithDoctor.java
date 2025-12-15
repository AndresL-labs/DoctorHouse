package com.example.doctorhouse.domain.model;

import com.example.doctorhouse.domain.model.enums.AppointmentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentWithDoctor {
    private Long id;
    private LocalDateTime scheduledAt;
    private Integer duration;
    private AppointmentStatus status;

    // Doctor Details
    private String doctorName;
    private String doctorLastName;
    private String doctorSpecialty;
    private String doctorLicense;
}
