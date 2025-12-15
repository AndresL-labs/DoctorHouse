package com.example.doctorhouse.domain.port.out;

import com.example.doctorhouse.domain.model.Patient;
import java.util.Optional;

public interface PatientRepositoryPort {
    Patient save(Patient patient);

    Optional<Patient> findById(Long id);

    Optional<Patient> findByEmail(String email);

    Optional<Patient> findByDni(String dni);
}
