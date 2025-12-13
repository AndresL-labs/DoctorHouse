package com.example.doctorhouse.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Modelo de dominio mínimo que representa la información necesaria
 * para la "Mi Ruta" del médico.
 */
public class AppointmentRouteModel {

    private UUID appointmentId;
    private UUID patientId;
    private String patientName;
    private String patientAddress;
    private String patientCondition; // nullable
    private LocalDateTime startAt;
    private LocalDateTime createdAt;
    private String status; // PROGRAMADA | FINALIZADA

    public AppointmentRouteModel() {}

    // Constructor completo
    public AppointmentRouteModel(UUID appointmentId, UUID patientId, String patientName,
                                 String patientAddress, String patientCondition,
                                 LocalDateTime startAt, LocalDateTime createdAt, String status) {
        this.appointmentId = appointmentId;
        this.patientId = patientId;
        this.patientName = patientName;
        this.patientAddress = patientAddress;
        this.patientCondition = patientCondition;
        this.startAt = startAt;
        this.createdAt = createdAt;
        this.status = status;
    }

    // Getters & Setters
    public UUID getAppointmentId() { return appointmentId; }
    public void setAppointmentId(UUID appointmentId) { this.appointmentId = appointmentId; }

    public UUID getPatientId() { return patientId; }
    public void setPatientId(UUID patientId) { this.patientId = patientId; }

    public String getPatientName() { return patientName; }
    public void setPatientName(String patientName) { this.patientName = patientName; }

    public String getPatientAddress() { return patientAddress; }
    public void setPatientAddress(String patientAddress) { this.patientAddress = patientAddress; }

    public String getPatientCondition() { return patientCondition; }
    public void setPatientCondition(String patientCondition) { this.patientCondition = patientCondition; }

    public LocalDateTime getStartAt() { return startAt; }
    public void setStartAt(LocalDateTime startAt) { this.startAt = startAt; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
