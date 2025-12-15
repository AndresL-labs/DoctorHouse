package com.example.doctorhouse.domain.model;

public class DoctorModel {
    private Long id;
    private String specialty;
    private String licenseNumber;
    private boolean active;

    public DoctorModel() {
    }

    public DoctorModel(Long id, String specialty, String licenseNumber, boolean active) {
        this.id = id;
        this.specialty = specialty;
        this.licenseNumber = licenseNumber;
        this.active = active;
    }

    // Getters y setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
