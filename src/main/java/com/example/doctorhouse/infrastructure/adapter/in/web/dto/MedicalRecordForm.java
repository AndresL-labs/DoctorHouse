package com.example.doctorhouse.infrastructure.adapter.in.web.dto;

import com.example.doctorhouse.domain.model.MedicalRecord;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalTime;

public class MedicalRecordForm {

    private Long id;
    private Long appointmentId;

    @NotBlank(message = "Diagnosis is required")
    private String diagnosis;

    @NotBlank(message = "Treatment is required")
    private String treatment;

    @NotBlank(message = "Observations are required")
    private String observations;

    @NotBlank(message = "Blood Pressure is required")
    private String bloodPressure;

    @NotNull(message = "Heart Rate is required")
    @Min(value = 1, message = "Heart Rate must be greater than 0")
    private Integer heartRate;

    @NotNull(message = "Weight is required")
    @DecimalMin(value = "0.1", message = "Weight must be greater than 0")
    @DecimalMax(value = "250.0", message = "Weight cannot exceed 250 kg")
    private Double weightKg;

    @NotNull(message = "Start Time is required")
    private LocalTime startedAt;

    @NotNull(message = "Finish Time is required")
    private LocalTime finishedAt;

    public MedicalRecordForm() {
    }

    public MedicalRecordForm(Long id, Long appointmentId, String diagnosis, String treatment, String observations,
            String bloodPressure, Integer heartRate, Double weightKg, LocalTime startedAt, LocalTime finishedAt) {
        this.id = id;
        this.appointmentId = appointmentId;
        this.diagnosis = diagnosis;
        this.treatment = treatment;
        this.observations = observations;
        this.bloodPressure = bloodPressure;
        this.heartRate = heartRate;
        this.weightKg = weightKg;
        this.startedAt = startedAt;
        this.finishedAt = finishedAt;
    }

    @AssertTrue(message = "Start time must be before finish time")
    public boolean isTimeRangeValid() {
        if (startedAt == null || finishedAt == null) {
            return true;
        }
        return startedAt.isBefore(finishedAt);
    }

    public MedicalRecord toDomain() {
        MedicalRecord record = new MedicalRecord();
        record.setId(this.id);
        record.setAppointmentId(this.appointmentId);
        record.setDiagnosis(this.diagnosis);
        record.setTreatment(this.treatment);
        record.setObservations(this.observations);
        record.setBloodPressure(this.bloodPressure);
        record.setHeartRate(this.heartRate);
        record.setWeightKg(this.weightKg);
        record.setStartedAt(this.startedAt);
        record.setFinishedAt(this.finishedAt);
        return record;
    }

    public static MedicalRecordForm fromDomain(MedicalRecord domain) {
        if (domain == null)
            return new MedicalRecordForm();
        return new MedicalRecordForm(
                domain.getId(),
                domain.getAppointmentId(),
                domain.getDiagnosis(),
                domain.getTreatment(),
                domain.getObservations(),
                domain.getBloodPressure(),
                domain.getHeartRate(),
                domain.getWeightKg(),
                domain.getStartedAt(),
                domain.getFinishedAt());
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(Long appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getTreatment() {
        return treatment;
    }

    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    public String getBloodPressure() {
        return bloodPressure;
    }

    public void setBloodPressure(String bloodPressure) {
        this.bloodPressure = bloodPressure;
    }

    public Integer getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(Integer heartRate) {
        this.heartRate = heartRate;
    }

    public Double getWeightKg() {
        return weightKg;
    }

    public void setWeightKg(Double weightKg) {
        this.weightKg = weightKg;
    }

    public LocalTime getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(LocalTime startedAt) {
        this.startedAt = startedAt;
    }

    public LocalTime getFinishedAt() {
        return finishedAt;
    }

    public void setFinishedAt(LocalTime finishedAt) {
        this.finishedAt = finishedAt;
    }
}
