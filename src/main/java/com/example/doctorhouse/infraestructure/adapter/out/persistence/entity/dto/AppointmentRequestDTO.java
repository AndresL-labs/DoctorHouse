package com.example.doctorhouse.infraestructure.adapter.out.persistence.entity.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class AppointmentRequestDTO {

    @NotNull(message = "El paciente es obligatorio")
    private Long patientId;

    @NotNull(message = "El médico es obligatorio")
    private Long doctorId;

    @NotNull(message = "La fecha es obligatoria")
    @Future(message = "La cita debe ser en el futuro")
    private LocalDateTime startDateTime;

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

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }
}
