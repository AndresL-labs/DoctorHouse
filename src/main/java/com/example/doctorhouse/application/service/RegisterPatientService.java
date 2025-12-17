package com.example.doctorhouse.application.service;

import com.example.doctorhouse.domain.model.Patient;
import com.example.doctorhouse.domain.port.in.RegisterPatientUseCase;
import com.example.doctorhouse.domain.port.out.PatientRepositoryPort;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

public class RegisterPatientService implements RegisterPatientUseCase {

    private final PatientRepositoryPort patientRepositoryPort;
    private final PasswordEncoder passwordEncoder;

    public RegisterPatientService(PatientRepositoryPort patientRepositoryPort,
                                  PasswordEncoder passwordEncoder) {
        this.patientRepositoryPort = patientRepositoryPort;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Patient registerPatient(Patient patient) {
        if (patientRepositoryPort.findByEmail(patient.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email ya existe.");
        }
        if (patientRepositoryPort.findByDni(patient.getDni()).isPresent()) {
            throw new IllegalArgumentException("DNI ya existe.");
        }
        patient.setCreatedAt(LocalDateTime.now());

        String encodedPassword = passwordEncoder.encode(patient.getPassword());
        patient.setPassword(encodedPassword);

        return patientRepositoryPort.save(patient);
    }
}
