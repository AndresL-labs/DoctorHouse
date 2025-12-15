package com.example.doctorhouse.domain.model;

import com.example.doctorhouse.domain.model.enums.AppointmentStatus;

import java.time.LocalDateTime;

public class Appointment {

    private Long id;
    private Long patientId;
    private Long doctorId;
    private LocalDateTime scheduledAt;
    private Integer duration; // Minutos
    private AppointmentStatus status;
    private LocalDateTime createdAt;

    public Appointment() {
    }

    public Appointment(Long id, Long patientId, Long doctorId, LocalDateTime scheduledAt, Integer duration, AppointmentStatus status, LocalDateTime createdAt) {
        this.id = id;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.scheduledAt = scheduledAt;
        this.duration = duration;
        this.status = status;
        this.createdAt = createdAt;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getPatientId() { return patientId; }
    public void setPatientId(Long patientId) { this.patientId = patientId; }

    public Long getDoctorId() { return doctorId; }
    public void setDoctorId(Long doctorId) { this.doctorId = doctorId; }

    public LocalDateTime getScheduledAt() { return scheduledAt; }
    public void setScheduledAt(LocalDateTime scheduledAt) { this.scheduledAt = scheduledAt; }

    public Integer getDuration() { return duration; }
    public void setDuration(Integer duration) { this.duration = duration; }

    public AppointmentStatus getStatus() { return status; }
    public void setStatus(AppointmentStatus status) { this.status = status; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

}