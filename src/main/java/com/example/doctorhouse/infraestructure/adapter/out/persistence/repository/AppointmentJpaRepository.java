package com.example.doctorhouse.infraestructure.adapter.out.persistence.repository;

import com.example.doctorhouse.infraestructure.adapter.out.persistence.entity.AppointmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface AppointmentJpaRepository extends JpaRepository<AppointmentEntity, Integer> {

    List<AppointmentEntity> findByDoctorIdAndAppointmentDate(
            Integer doctorId,
            LocalDate appointmentDate
    );
}
