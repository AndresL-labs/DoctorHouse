package com.example.doctorhouse.application.service;

import com.example.doctorhouse.domain.model.Doctor;
import com.example.doctorhouse.domain.port.in.ManageDoctorsUseCase;
import com.example.doctorhouse.domain.port.out.DoctorRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ManageDoctorsService implements ManageDoctorsUseCase {

    private final DoctorRepositoryPort doctorRepositoryPort;

    @Override
    public void activateDoctor(Long id) {
        Doctor doctor = doctorRepositoryPort.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));
        doctor.setActive(true);
        doctorRepositoryPort.save(doctor);
    }

    @Override
    public void deactivateDoctor(Long id) {
        Doctor doctor = doctorRepositoryPort.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));
        doctor.setActive(false);
        doctorRepositoryPort.save(doctor);
    }
}
