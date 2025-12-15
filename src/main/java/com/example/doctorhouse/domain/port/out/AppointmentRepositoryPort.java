package com.example.doctorhouse.domain.port.out;

import com.example.doctorhouse.domain.model.Appointment;
import com.example.doctorhouse.domain.model.AppointmentWithDoctor; // Added import
import java.util.Optional; // Added import

public interface AppointmentRepositoryPort {
    Appointment save(Appointment appointment);

    java.util.List<com.example.doctorhouse.domain.model.AppointmentWithPatient> findByDoctorEmailAndDateRange(
            String email, java.time.LocalDateTime start, java.time.LocalDateTime end);

    java.util.List<AppointmentWithDoctor> findByPatientEmail(String email); // Changed to use imported class

    java.util.List<Appointment> findByDoctorIdAndDate(Long doctorId, java.time.LocalDateTime start,
            java.time.LocalDateTime end);

    Optional<Appointment> findById(Long id); // Changed to use imported class
}
