package com.example.doctorhouse.infrastructure.adapter.out.persistence.repository;

import com.example.doctorhouse.infrastructure.adapter.out.persistence.entity.MedicalRecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MedicalRecordJpaRepository extends JpaRepository<MedicalRecordEntity, Long> {
    Optional<MedicalRecordEntity> findByAppointment_Id(Long appointmentId);
}
