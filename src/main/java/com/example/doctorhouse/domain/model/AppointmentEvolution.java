package com.example.doctorhouse.domain.model;


import java.time.LocalDateTime;

public class AppointmentEvolution {

    private final Long id;
    private final Long appointmentId;
    private final Long doctorId;
    private final String bloodPressure;
    private final Integer heartRate;
    private final Double temperature;
    private final Integer oxygenSaturation;
    private final String diagnosis;
    private final String observations;
    private final LocalDateTime createdAt;

    private AppointmentEvolution(Long id,
                                 Long appointmentId,
                                 Long doctorId,
                                 String bloodPressure,
                                 Integer heartRate,
                                 Double temperature,
                                 Integer oxygenSaturation,
                                 String diagnosis,
                                 String observations,
                                 LocalDateTime createdAt) {

        this.id = id;
        this.appointmentId = appointmentId;
        this.doctorId = doctorId;
        this.bloodPressure = bloodPressure;
        this.heartRate = heartRate;
        this.temperature = temperature;
        this.oxygenSaturation = oxygenSaturation;
        this.diagnosis = diagnosis;
        this.observations = observations;
        this.createdAt = createdAt;
    }

    // Factory Method
    public static AppointmentEvolution create(Long appointmentId,
                                              Long doctorId,
                                              String bloodPressure,
                                              Integer heartRate,
                                              Double temperature,
                                              Integer oxygenSaturation,
                                              String diagnosis,
                                              String observations) {

        //validaciones tengo que implementar
        //ej  if (appointmentId == null || appointmentId <= 0) {
        //            throw new IllegalArgumentException("appointmentId must be valid");
        //        }

        return new AppointmentEvolution(
                null,
                appointmentId,
                doctorId,
                bloodPressure,
                heartRate,
                temperature,
                oxygenSaturation,
                diagnosis,
                observations,
                LocalDateTime.now()
        );
    }

    // Getters (sin setters para mantener el dominio consistente)
    public Long getId() { return id; }
    public Long getAppointmentId() { return appointmentId; }
    public Long getDoctorId() { return doctorId; }
    public String getBloodPressure() { return bloodPressure; }
    public Integer getHeartRate() { return heartRate; }
    public Double getTemperature() { return temperature; }
    public Integer getOxygenSaturation() { return oxygenSaturation; }
    public String getDiagnosis() { return diagnosis; }
    public String getObservations() { return observations; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
