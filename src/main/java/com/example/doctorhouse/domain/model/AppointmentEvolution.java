package com.example.doctorhouse.domain.model;

import java.time.LocalDateTime;

public class AppointmentEvolution {

    private final Long id;
    private final Long appointmentId;
    private final String bloodPressure;
    private final Integer heartRate;
    private final Double temperature;
    private final Integer oxygenSaturation;
    private final Double weightKg;
    private final String diagnosis;
    private final String observations;
    private final LocalDateTime createdAt;

    private AppointmentEvolution(Long id,
                                 Long appointmentId,
                                 String bloodPressure,
                                 Integer heartRate,
                                 Double temperature,
                                 Integer oxygenSaturation,
                                 Double weightKg,
                                 String diagnosis,
                                 String observations,
                                 LocalDateTime createdAt) {

        this.id = id;
        this.appointmentId = appointmentId;
        this.bloodPressure = bloodPressure;
        this.heartRate = heartRate;
        this.temperature = temperature;
        this.oxygenSaturation = oxygenSaturation;
        this.weightKg = weightKg;
        this.diagnosis = diagnosis;
        this.observations = observations;
        this.createdAt = createdAt;
    }

    // Factory Method
    public static AppointmentEvolution create(Long appointmentId,
                                              String bloodPressure,
                                              Integer heartRate,
                                              Double temperature,
                                              Integer oxygenSaturation,
                                              Double weightKg,
                                              String diagnosis,
                                              String observations) {

        if (appointmentId == null || appointmentId <= 0) {
            throw new IllegalArgumentException("appointmentId must be valid");
        }

        if (heartRate != null && heartRate <= 0) {
            throw new IllegalArgumentException("heartRate must be positive");
        }

        if (temperature != null && (temperature < 30 || temperature > 45)) {
            throw new IllegalArgumentException("temperature out of valid range");
        }

        if (oxygenSaturation != null && (oxygenSaturation < 0 || oxygenSaturation > 100)) {
            throw new IllegalArgumentException("oxygenSaturation must be between 0 and 100");
        }

        if (weightKg != null && weightKg <= 0) {
            throw new IllegalArgumentException("weightKg must be positive");
        }

        return new AppointmentEvolution(
                null,
                appointmentId,
                bloodPressure,
                heartRate,
                temperature,
                oxygenSaturation,
                weightKg,
                diagnosis,
                observations,
                LocalDateTime.now()
        );
    }

    // Getters (sin setters para mantener el dominio consistente)
    public Long getId() { return id; }
    public Long getAppointmentId() { return appointmentId; }
    public String getBloodPressure() { return bloodPressure; }
    public Integer getHeartRate() { return heartRate; }
    public Double getTemperature() { return temperature; }
    public Integer getOxygenSaturation() { return oxygenSaturation; }
    public Double getWeightKg() { return weightKg; }
    public String getDiagnosis() { return diagnosis; }
    public String getObservations() { return observations; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
