package com.example.doctorhouse.infraestructure.adapter.out.persistence.entity.dto;

import com.example.doctorhouse.domain.model.enums.AppointmentStatus;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class AppointmentResponseDTO {

    private Long idAppointment;
    private Long patientId;
    private Long doctorId;
    private LocalDateTime appointmentDateTime;
    private LocalTime startAt;
    private LocalTime endAt;
    private AppointmentStatus status;

    public AppointmentResponseDTO() {
    }

    public AppointmentResponseDTO(Long idAppointment, Long patientId, Long doctorId,
                                  LocalDateTime appointmentDateTime, LocalTime startAt, LocalTime endAt, AppointmentStatus status) {
        this.idAppointment = idAppointment;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.appointmentDateTime = appointmentDateTime;
        this.startAt = startAt;
        this.endAt = endAt;
        this.status = status;
    }

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

    public LocalTime getEndAt() {
        return endAt;
    }

    public void setEndAt(LocalTime endAt) {
        this.endAt = endAt;
    }

    public AppointmentStatus getStatus() {
        return status;
    }

    public void setStatus(AppointmentStatus status) {
        this.status = status;
    }
}
