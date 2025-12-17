package com.example.doctorhouse.application.service;

import com.example.doctorhouse.domain.model.Doctor;
import com.example.doctorhouse.domain.port.in.RegisterDoctorUseCase;
import com.example.doctorhouse.domain.port.out.DoctorRepositoryPort;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

public class RegisterDoctorService implements RegisterDoctorUseCase {

    private final DoctorRepositoryPort doctorRepositoryPort;
    private final PasswordEncoder passwordEncoder;

    public RegisterDoctorService(DoctorRepositoryPort doctorRepositoryPort,
                                 PasswordEncoder passwordEncoder) {
        this.doctorRepositoryPort = doctorRepositoryPort;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Doctor registerDoctor(Doctor doctor) {
        if (doctorRepositoryPort.findByEmail(doctor.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email ya existe en el sistema");
        }
        if (doctorRepositoryPort.findByLicenseNumber(doctor.getLicenseNumber()).isPresent()) {
            throw new IllegalArgumentException("Número de licencia ya existe en el sistema");
        }
        doctor.setCreatedAt(LocalDateTime.now());

        String encodedPassword = passwordEncoder.encode(doctor.getPassword());
        doctor.setPassword(encodedPassword);

        return doctorRepositoryPort.save(doctor);
    }
}
