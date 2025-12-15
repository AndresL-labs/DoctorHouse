package com.example.doctorhouse.infrastructure.adapter.out.persistence.repository;

import com.example.doctorhouse.infrastructure.adapter.out.persistence.entity.AppointmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentJpaRepository extends JpaRepository<AppointmentEntity, Long> {
    List<AppointmentEntity> findByDoctor_EmailAndScheduledAtBetweenOrderByScheduledAtAsc(String email,
            LocalDateTime start, LocalDateTime end);

    List<AppointmentEntity> findByPatient_EmailOrderByScheduledAtDesc(String email);

    List<AppointmentEntity> findByDoctor_IdAndScheduledAtBetweenOrderByScheduledAtAsc(Long doctorId,
            java.time.LocalDateTime start, java.time.LocalDateTime end);
}
