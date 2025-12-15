package com.example.doctorhouse.domain.port.out;

import com.example.doctorhouse.domain.model.Doctor;
import java.util.Optional;

public interface DoctorRepositoryPort {
    Doctor save(Doctor doctor);

    Optional<Doctor> findById(Long id);

    Optional<Doctor> findByEmail(String email);

    Optional<Doctor> findByLicenseNumber(String licenseNumber);

    java.util.List<Doctor> findAll();
}
