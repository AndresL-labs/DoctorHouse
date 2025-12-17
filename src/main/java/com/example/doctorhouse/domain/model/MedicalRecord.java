package com.example.doctorhouse.domain.model;

import java.time.LocalTime;
import java.util.Objects;

public class MedicalRecord {
    private Long id;
    private Long appointmentId;
    private String diagnosis;
    private String treatment;
    private String observations;
    private String bloodPressure;
    private Integer heartRate;
    private Double weightKg;
    private LocalTime startedAt;
    private LocalTime finishedAt;

    public MedicalRecord() {
    }

    public MedicalRecord(Long id, Long appointmentId, String diagnosis, String treatment, String observations,
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

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        MedicalRecord that = (MedicalRecord) o;
        return Objects.equals(id, that.id) && Objects.equals(appointmentId, that.appointmentId)
                && Objects.equals(diagnosis, that.diagnosis) && Objects.equals(treatment, that.treatment)
                && Objects.equals(observations, that.observations) && Objects.equals(bloodPressure, that.bloodPressure)
                && Objects.equals(heartRate, that.heartRate) && Objects.equals(weightKg, that.weightKg)
                && Objects.equals(startedAt, that.startedAt) && Objects.equals(finishedAt, that.finishedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, appointmentId, diagnosis, treatment, observations, bloodPressure, heartRate, weightKg,
                startedAt, finishedAt);
    }

    @Override
    public String toString() {
        return "MedicalRecord{" +
                "id=" + id +
                ", appointmentId=" + appointmentId +
                ", diagnosis='" + diagnosis + '\'' +
                ", treatment='" + treatment + '\'' +
                ", observations='" + observations + '\'' +
                ", bloodPressure='" + bloodPressure + '\'' +
                ", heartRate=" + heartRate +
                ", weightKg=" + weightKg +
                ", startedAt=" + startedAt +
                ", finishedAt=" + finishedAt +
                '}';
    }
}
