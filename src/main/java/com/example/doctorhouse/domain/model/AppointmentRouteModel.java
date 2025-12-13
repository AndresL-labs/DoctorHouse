package com.example.doctorhouse.domain.model;

import java.time.LocalTime;

public class AppointmentRouteModel {

    private Integer appointmentId;
    private LocalTime startTime;
    private String patientFullName;
    private String patientAddress;
    private String patientCondition;
    private String status;

    public AppointmentRouteModel(
            Integer appointmentId,
            LocalTime startTime,
            String patientFullName,
            String patientAddress,
            String patientCondition,
            String status
    ) {
        this.appointmentId = appointmentId;
        this.startTime = startTime;
        this.patientFullName = patientFullName;
        this.patientAddress = patientAddress;
        this.patientCondition = patientCondition;
        this.status = status;
    }

    public Integer getAppointmentId() { return appointmentId; }
    public LocalTime getStartTime() { return startTime; }
    public String getPatientFullName() { return patientFullName; }
    public String getPatientAddress() { return patientAddress; }
    public String getPatientCondition() { return patientCondition; }
    public String getStatus() { return status; }
}
