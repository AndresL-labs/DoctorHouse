package com.example.doctorhouse.infraestructure.adapter.out.persistence.repository;

import com.example.doctorhouse.infraestructure.adapter.out.persistence.entity.AppointmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface AppointmentJpaRepository extends JpaRepository<AppointmentEntity, Long> {

    // CONSULTA PERSONALIZADA
    @Query("SELECT a FROM AppointmentEntity a " +
            "WHERE a.doctorId = :doctorId " +
            "AND a.appointmentDate = :date " +
            "AND a.startAt BETWEEN :startTime AND :endTime")
    List<AppointmentEntity> findAppointmentsEnRango(
            @Param("doctorId") Long doctorId,
            @Param("date") LocalDate date,         // Solo la fecha
            @Param("startTime") LocalTime startTime, // Hora inicio
            @Param("endTime") LocalTime endTime      // Hora fin
    );
}
