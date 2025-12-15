package com.example.doctorhouse.infraestructure.adapter.out.persistence.repository;

import com.example.doctorhouse.infraestructure.adapter.out.persistence.entity.AppointmentEvolutionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AppointmentEvolutionJpaRepository extends JpaRepository<AppointmentEvolutionEntity, Long> {
    Optional<AppointmentEvolutionEntity> findByAppointmentId(Long appointmentId);
}


