package com.example.doctorhouse.infrastructure.adapter.out.persistence.repository;

import com.example.doctorhouse.infrastructure.adapter.out.persistence.entity.DoctorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface DoctorJpaRepository extends JpaRepository<DoctorEntity, Long> {
    Optional<DoctorEntity> findByEmail(String email);

    Optional<DoctorEntity> findByLicenseNumber(String licenseNumber);
}
