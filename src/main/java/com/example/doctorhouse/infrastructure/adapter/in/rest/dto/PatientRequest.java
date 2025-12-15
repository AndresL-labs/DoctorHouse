package com.example.doctorhouse.infrastructure.adapter.in.rest.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDate;

@Data
public class PatientRequest {
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

    @NotNull
    private LocalDate birthDate;
    private String bloodType;
    private String allergies;
    private String address;
}
