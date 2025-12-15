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
        // ---------------------------------------------------------
        // AQUÍ VA TU LÓGICA DE NEGOCIO PURA
        // ---------------------------------------------------------

        // 1. Validaciones de Negocio (Ejemplos):
        // - ¿El doctor tiene disponibilidad en ese horario?
        // - ¿El paciente ya tiene una cita a esa misma hora?
        // - ¿La duración es válida para esa especialidad?

        // Por ahora, como estamos empezando, confiamos en la validación básica
        // y pasamos directamente a persistir.

        // 2. Guardar usando el Puerto de Salida
        return appointmentRepository.save(appointment);
    }
}
