package com.example.doctorhouse.application.service;

import com.example.doctorhouse.domain.model.AppointmentEvolution;
import com.example.doctorhouse.domain.model.AppointmentModel;
import com.example.doctorhouse.domain.model.dto.command.RegisterAppointmentEvolutionCommand;
import com.example.doctorhouse.domain.model.enums.AppointmentStatus;
import com.example.doctorhouse.domain.port.in.RegisterAppointmentEvolutionUseCase;
import com.example.doctorhouse.domain.port.out.AppointmentEvolutionRepositoryPort;
import com.example.doctorhouse.domain.port.out.AppointmentRepositoryPort;
import com.example.doctorhouse.domain.port.out.SecurityPort;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;

@Service
@Transactional
public class RegisterAppointmentEvolutionService implements RegisterAppointmentEvolutionUseCase {

    private final AppointmentEvolutionRepositoryPort evolutionRepository;
    private final AppointmentRepositoryPort appointmentRepository;
    private final SecurityPort securityPort;

    public RegisterAppointmentEvolutionService(
            AppointmentEvolutionRepositoryPort evolutionRepository,
            AppointmentRepositoryPort appointmentRepository,
            SecurityPort securityPort) {
        this.evolutionRepository = evolutionRepository;
        this.appointmentRepository = appointmentRepository;
        this.securityPort = securityPort;
    }

    @Override
    public AppointmentEvolution registerEvolution(RegisterAppointmentEvolutionCommand command) {
        // 1. Get authenticated user
        Long authenticatedDoctorId = securityPort.getAuthenticatedUserId()
                .orElseThrow(() -> new IllegalStateException("User not authenticated"));

        // 2. Find the appointment
        AppointmentModel appointment = appointmentRepository.findById(command.appointmentId())
                .orElseThrow(() -> new IllegalArgumentException("Appointment not found"));

        // 3. Validate that the appointment belongs to the authenticated doctor
        if (!appointment.getDoctorId().equals(authenticatedDoctorId)) {
            throw new SecurityException("The appointment does not belong to the authenticated doctor");
        }

        // 4. Validate that no previous evolution exists (1:1 relationship)
        if (evolutionRepository.findByAppointmentId(command.appointmentId()).isPresent()) {
            throw new IllegalArgumentException("The appointment already has a clinical evolution registered");
        }

        // 5. Validate appointment status (only SCHEDULED allows evolution registration)
        if (appointment.getStatus() != AppointmentStatus.SCHEDULED) {
            throw new IllegalArgumentException(
                    "The appointment does not allow clinical evolution registration. Current status: " + appointment.getStatus());
        }

        // 6. Create the evolution (Domain)
        AppointmentEvolution evolution = AppointmentEvolution.create(
                command.appointmentId(),
                command.bloodPressure(),
                command.heartRate(),
                command.temperature(),
                command.oxygenSaturation(),
                command.weightKg(),
                command.diagnosis(),
                command.observations());

        // 7. Update the appointment (Finalize)
        appointment.setStatus(AppointmentStatus.COMPLETED);
        // Use endAt as the actual completion time
        appointment.setEndAt(LocalTime.now());

        // 8. Persist changes (Transactionality should be handled by the framework)
        AppointmentEvolution savedEvolution = evolutionRepository.save(evolution);
        appointmentRepository.save(appointment);

        return savedEvolution;
    }
}
