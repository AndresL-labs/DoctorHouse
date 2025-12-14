package com.example.doctorhouse.infraestructure.adapter.out.persistence.repository;

import com.example.doctorhouse.infraestructure.adapter.out.persistence.entity.DoctorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DoctorJpaRepository extends JpaRepository<DoctorEntity, Long> {
    List<DoctorEntity> findByActiveTrue();
}
