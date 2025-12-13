package com.example.doctorhouse.infraestructure.adapter.out.persistence.entity;

import com.example.doctorhouse.domain.model.enums.AppointmentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate; // OJO: Cambiado de LocalDateTime a LocalDate
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "appointments")
@Getter             // Genera todos los Getters
@Setter             // Genera todos los Setters
@NoArgsConstructor  // Genera constructor vacío (Obligatorio para JPA)
@AllArgsConstructor // Genera constructor con todos los argumentos (Útil)
public class AppointmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "patient_id", nullable = false)
    private Long patientId;

    @Column(name = "doctor_id", nullable = false)
    private Long doctorId;

    // CORRECCIÓN CRÍTICA: Cambiado a LocalDate
    @Column(name = "appointment_date", nullable = false)
    private LocalDate appointmentDate;

    @Column(name = "start_time", nullable = false)
    private LocalTime startAt;

    @Column(name = "end_time")
    private LocalTime endAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AppointmentStatus status;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    // Ya no necesitas escribir Getters, Setters ni Constructores manualmente.
}