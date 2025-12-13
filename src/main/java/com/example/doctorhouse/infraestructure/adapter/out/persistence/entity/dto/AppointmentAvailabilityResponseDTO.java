package com.example.doctorhouse.infraestructure.adapter.out.persistence.entity.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class AppointmentAvailabilityResponseDTO {

    private Long doctorId;
    private LocalDate date;
    private List<LocalTime> availableTimes;

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<LocalTime> getAvailableTimes() {
        return availableTimes;
    }

    public void setAvailableTimes(List<LocalTime> availableTimes) {
        this.availableTimes = availableTimes;
    }
}
