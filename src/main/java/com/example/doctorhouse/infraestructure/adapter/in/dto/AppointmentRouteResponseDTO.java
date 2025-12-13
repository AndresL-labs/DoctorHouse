package com.example.doctorhouse.infraestructure.adapter.in.dto;

import java.util.UUID;

/**
 * DTO de respuesta para el endpoint "mi ruta".
 * startTime se devuelve como String (por ejemplo "09:00" o ISO local datetime).
 */
public class AppointmentRouteResponseDTO {

    private UUID appointmentId;
    private String startTime; // formato ISO o "HH:mm"
    private String patientName;
    private String patientAddress;
    private String patientCondition;
    private String status;

    public AppointmentRouteResponseDTO() {}

    // Getters y setters
    public UUID getAppointmentId() { return appointmentId; }
    public void setAppointmentId(UUID appointmentId) { this.appointmentId = appointmentId; }

    public String getStartTime() { return startTime; }
    public void setStartTime(String startTime) { this.startTime = startTime; }

    public String getPatientName() { return patientName; }
    public void setPatientName(String patientName) { this.patientName = patientName; }

    public String getPatientAddress() { return patientAddress; }
    public void setPatientAddress(String patientAddress) { this.patientAddress = patientAddress; }

    public String getPatientCondition() { return patientCondition; }
    public void setPatientCondition(String patientCondition) { this.patientCondition = patientCondition; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
