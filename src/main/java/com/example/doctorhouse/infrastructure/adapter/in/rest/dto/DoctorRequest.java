package com.example.doctorhouse.infrastructure.adapter.in.rest.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class DoctorRequest {
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank
    private String dni;
    @NotBlank
    @Email
    private String email;
    private String phone;
    @NotBlank
    private String password;
    @NotBlank
    private String specialty;
    @NotBlank
    private String licenseNumber;
}
