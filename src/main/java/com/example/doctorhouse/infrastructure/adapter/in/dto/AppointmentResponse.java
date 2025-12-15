package com.example.doctorhouse.infrastructure.adapter.in.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentResponse {

    private Long id;
    private Long patientId;
    private Long doctorId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime scheduledAt;

    private Integer duration;

    private String status;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    public String getEndsAt() {
        if (scheduledAt != null && duration != null) {
            return scheduledAt.plusMinutes(duration).toString();
        }
        return null;
    }
}