package com.example.doctorhouse.domain.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Patient {
    private Long id;
    private String firstName;
    private String lastName;
    private String dni;
    private String email;
    private String phone;
    private String password;
    private Boolean active;

    private LocalDate birthDate;
    private String bloodType;
    private String allergies;
    private String address;

    private LocalDateTime createdAt;

    public Patient() {
    }

    public Patient(LocalDateTime createdAt, String address, String allergies, String bloodType, LocalDate birthDate,
            Boolean active, String password, String phone, String email, String dni, String lastName,
            String firstName, Long id) {
        this.createdAt = createdAt;
        this.address = address;
        this.allergies = allergies;
        this.bloodType = bloodType;
        this.birthDate = birthDate;
        this.active = active;
        this.password = password;
        this.phone = phone;
        this.email = email;
        this.dni = dni;
        this.lastName = lastName;
        this.firstName = firstName;
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public String getAllergies() {
        return allergies;
    }

    public void setAllergies(String allergies) {
        this.allergies = allergies;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}