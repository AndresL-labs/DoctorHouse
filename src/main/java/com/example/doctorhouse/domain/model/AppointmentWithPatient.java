package com.example.doctorhouse.domain.model;

import com.example.doctorhouse.domain.model.enums.AppointmentStatus;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public class AppointmentWithPatient {
    private Long id;
    private LocalDateTime scheduledAt;
    private Integer duration;
    private AppointmentStatus status;

    // Patient Details
    private String patientName;
    private String patientLastName;
    private LocalDate patientBirthDate;
    private String patientAllergies;
    private String patientBloodType;
    private String patientAddress;
    private String patientPhone;

    public AppointmentWithPatient() {
    }

    public AppointmentWithPatient(Long id, LocalDateTime scheduledAt, Integer duration, AppointmentStatus status,
            String patientName, String patientLastName, LocalDate patientBirthDate, String patientAllergies,
            String patientBloodType, String patientAddress, String patientPhone) {
        this.id = id;
        this.scheduledAt = scheduledAt;
        this.duration = duration;
        this.status = status;
        this.patientName = patientName;
        this.patientLastName = patientLastName;
        this.patientBirthDate = patientBirthDate;
        this.patientAllergies = patientAllergies;
        this.patientBloodType = patientBloodType;
        this.patientAddress = patientAddress;
        this.patientPhone = patientPhone;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getScheduledAt() {
        return scheduledAt;
    }

    public void setScheduledAt(LocalDateTime scheduledAt) {
        this.scheduledAt = scheduledAt;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public AppointmentStatus getStatus() {
        return status;
    }

    public void setStatus(AppointmentStatus status) {
        this.status = status;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPatientLastName() {
        return patientLastName;
    }

    public void setPatientLastName(String patientLastName) {
        this.patientLastName = patientLastName;
    }

    public LocalDate getPatientBirthDate() {
        return patientBirthDate;
    }

    public void setPatientBirthDate(LocalDate patientBirthDate) {
        this.patientBirthDate = patientBirthDate;
    }

    public String getPatientAllergies() {
        return patientAllergies;
    }

    public void setPatientAllergies(String patientAllergies) {
        this.patientAllergies = patientAllergies;
    }

    public String getPatientBloodType() {
        return patientBloodType;
    }

    public void setPatientBloodType(String patientBloodType) {
        this.patientBloodType = patientBloodType;
    }

    public String getPatientAddress() {
        return patientAddress;
    }

    public void setPatientAddress(String patientAddress) {
        this.patientAddress = patientAddress;
    }

    public String getPatientPhone() {
        return patientPhone;
    }

    public void setPatientPhone(String patientPhone) {
        this.patientPhone = patientPhone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        AppointmentWithPatient that = (AppointmentWithPatient) o;
        return Objects.equals(id, that.id) && Objects.equals(scheduledAt, that.scheduledAt)
                && Objects.equals(duration, that.duration) && status == that.status
                && Objects.equals(patientName, that.patientName)
                && Objects.equals(patientLastName, that.patientLastName)
                && Objects.equals(patientBirthDate, that.patientBirthDate)
                && Objects.equals(patientAllergies, that.patientAllergies)
                && Objects.equals(patientBloodType, that.patientBloodType)
                && Objects.equals(patientAddress, that.patientAddress)
                && Objects.equals(patientPhone, that.patientPhone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, scheduledAt, duration, status, patientName, patientLastName, patientBirthDate,
                patientAllergies, patientBloodType, patientAddress, patientPhone);
    }

    @Override
    public String toString() {
        return "AppointmentWithPatient{" +
                "id=" + id +
                ", scheduledAt=" + scheduledAt +
                ", duration=" + duration +
                ", status=" + status +
                ", patientName='" + patientName + '\'' +
                ", patientLastName='" + patientLastName + '\'' +
                ", patientBirthDate=" + patientBirthDate +
                ", patientAllergies='" + patientAllergies + '\'' +
                ", patientBloodType='" + patientBloodType + '\'' +
                ", patientAddress='" + patientAddress + '\'' +
                ", patientPhone='" + patientPhone + '\'' +
                '}';
    }
}
