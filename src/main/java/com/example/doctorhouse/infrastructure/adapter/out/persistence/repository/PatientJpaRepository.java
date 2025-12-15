package com.example.doctorhouse.infrastructure.adapter.out.persistence.repository;

import com.example.doctorhouse.infrastructure.adapter.out.persistence.entity.PatientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PatientJpaRepository extends JpaRepository<PatientEntity, Long> {
    Optional<PatientEntity> findByEmail(String email);

    Optional<PatientEntity> findByDni(String dni);
}
