package com.example.doctorhouse.domain.model;

import com.example.doctorhouse.domain.model.enums.AppointmentStatus;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

/**
 * Modelo de dominio puro (sin anotaciones)
 */
public class AppointmentModel {

    public static final int DEFAULT_DURATION_MINUTES = 45;
    public static final int BUFFER_TIME_MINUTES = 60;

    private Long idAppointment;
    private Long patientId;
    private Long doctorId;
    private LocalDateTime appointmentDateTime;
    private LocalTime startAt;
    private Optional<LocalTime> endAt;
    private AppointmentStatus status;
    private LocalDateTime createdAt;
    private Optional<LocalDateTime> updatedAt;

    public AppointmentModel() {
        this.endAt = Optional.empty();
        this.updatedAt = Optional.empty();
    }

    public AppointmentModel(
            Long idAppointment,
            Long patientId,
            Long doctorId,
            LocalDateTime appointmentDateTime,
            LocalTime startAt,
            AppointmentStatus status,
            LocalDateTime createdAt) {
        this.idAppointment = idAppointment;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.appointmentDateTime = appointmentDateTime;
        this.startAt = startAt;
        this.status = status;
        this.createdAt = createdAt;
        this.endAt = Optional.empty();
        this.updatedAt = Optional.empty();
    }

    // ===== GETTERS & SETTERS =====

    public Long getIdAppointment() {
        return idAppointment;
    }

    public void setIdAppointment(Long idAppointment) {
        this.idAppointment = idAppointment;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public LocalDateTime getAppointmentDateTime() {
        return appointmentDateTime;
    }

    public void setAppointmentDateTime(LocalDateTime appointmentDateTime) {
        this.appointmentDateTime = appointmentDateTime;
    }

    public LocalTime getStartAt() {
        return startAt;
    }

    public void setStartAt(LocalTime startAt) {
        this.startAt = startAt;
    }

    public Optional<LocalTime> getEndAt() {
        return endAt;
    }

    public void setEndAt(LocalTime endAt) {
        this.endAt = Optional.ofNullable(endAt);
    }

    public AppointmentStatus getStatus() {
        return status;
    }

    public void setStatus(AppointmentStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Optional<LocalDateTime> getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = Optional.ofNullable(updatedAt);
    }
}
