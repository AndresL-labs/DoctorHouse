package com.example.doctorhouse.application.service;

import com.example.doctorhouse.domain.model.Appointment;
import com.example.doctorhouse.domain.port.in.CreateAppointmentUseCase;
import com.example.doctorhouse.domain.port.out.AppointmentRepositoryPort;
import jakarta.transaction.Transactional;

@Transactional
public class AppointmentService implements CreateAppointmentUseCase {

    private final AppointmentRepositoryPort appointmentRepository;

    public AppointmentService(AppointmentRepositoryPort appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    @Override
    public Appointment create(Appointment appointment) {
        if (appointment.getScheduledAt().isBefore(java.time.LocalDateTime.now())) {
            throw new IllegalArgumentException("La cita debe ser en el futuro");
        }
        if (appointment.getDuration() == null || appointment.getDuration() <= 0) {
            throw new IllegalArgumentException("La duración de la cita debe ser positiva");
        }

        // 2. Guardar usando el Puerto de Salida
        return appointmentRepository.save(appointment);
    }
}
