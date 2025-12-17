package com.example.doctorhouse.domain.model;

import com.example.doctorhouse.domain.model.enums.AppointmentStatus;
import java.time.LocalDateTime;
import java.util.Objects;

public class AppointmentWithDoctor {
    private Long id;
    private LocalDateTime scheduledAt;
    private Integer duration;
    private AppointmentStatus status;

    // Doctor Details
    private String doctorName;
    private String doctorLastName;
    private String doctorSpecialty;
    private String doctorLicense;

    public AppointmentWithDoctor() {
    }

    public AppointmentWithDoctor(Long id, LocalDateTime scheduledAt, Integer duration, AppointmentStatus status,
            String doctorName, String doctorLastName, String doctorSpecialty, String doctorLicense) {
        this.id = id;
        this.scheduledAt = scheduledAt;
        this.duration = duration;
        this.status = status;
        this.doctorName = doctorName;
        this.doctorLastName = doctorLastName;
        this.doctorSpecialty = doctorSpecialty;
        this.doctorLicense = doctorLicense;
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

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getDoctorLastName() {
        return doctorLastName;
    }

    public void setDoctorLastName(String doctorLastName) {
        this.doctorLastName = doctorLastName;
    }

    public String getDoctorSpecialty() {
        return doctorSpecialty;
    }

    public void setDoctorSpecialty(String doctorSpecialty) {
        this.doctorSpecialty = doctorSpecialty;
    }

    public String getDoctorLicense() {
        return doctorLicense;
    }

    public void setDoctorLicense(String doctorLicense) {
        this.doctorLicense = doctorLicense;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        AppointmentWithDoctor that = (AppointmentWithDoctor) o;
        return Objects.equals(id, that.id) && Objects.equals(scheduledAt, that.scheduledAt)
                && Objects.equals(duration, that.duration) && status == that.status
                && Objects.equals(doctorName, that.doctorName) && Objects.equals(doctorLastName, that.doctorLastName)
                && Objects.equals(doctorSpecialty, that.doctorSpecialty)
                && Objects.equals(doctorLicense, that.doctorLicense);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, scheduledAt, duration, status, doctorName, doctorLastName, doctorSpecialty,
                doctorLicense);
    }

    @Override
    public String toString() {
        return "AppointmentWithDoctor{" +
                "id=" + id +
                ", scheduledAt=" + scheduledAt +
                ", duration=" + duration +
                ", status=" + status +
                ", doctorName='" + doctorName + '\'' +
                ", doctorLastName='" + doctorLastName + '\'' +
                ", doctorSpecialty='" + doctorSpecialty + '\'' +
                ", doctorLicense='" + doctorLicense + '\'' +
                '}';
    }
}
