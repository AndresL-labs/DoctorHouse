package com.example.doctorhouse.application.service;

import com.example.doctorhouse.domain.model.AppointmentEvolution;
import com.example.doctorhouse.domain.model.dto.command.RegisterAppointmentEvolutionCommand;
import com.example.doctorhouse.domain.port.in.RegisterAppointmentEvolutionUseCase;
import com.example.doctorhouse.domain.port.out.AppointmentEvolutionRepositoryPort;
import com.example.doctorhouse.domain.port.out.AppointmentExistencePort;

public class RegisterAppointmentEvolutionService
        implements RegisterAppointmentEvolutionUseCase {

    private final AppointmentEvolutionRepositoryPort evolutionRepository;
    private final AppointmentExistencePort appointmentExistencePort;

    public RegisterAppointmentEvolutionService(
            AppointmentEvolutionRepositoryPort evolutionRepository,
            AppointmentExistencePort appointmentExistencePort
    ) {
        this.evolutionRepository = evolutionRepository;
        this.appointmentExistencePort = appointmentExistencePort;
    }

    @Override
    public void registerEvolution(RegisterAppointmentEvolutionCommand command) {

        // 1. Validate that the appointment exists
        if (!appointmentExistencePort.existsAppointment(command.appointmentId())) {
            throw new IllegalArgumentException("Appointment does not exist");
        }

        // 2. Create the evolution (domain)
        AppointmentEvolution evolution = AppointmentEvolution.create(
                command.appointmentId(),
                command.bloodPressure(),
                command.heartRate(),
                command.temperature(),
                command.oxygenSaturation(),
                command.weightKg(),
                command.diagnosis(),
                command.observations()
        );

        // 3. Persist evolution
        evolutionRepository.save(evolution);

        // TODO:
        // - validate appointment status (SCHEDULED / IN_PROGRESS)
        // - finalize appointment
        // - set completedAt timestamp
        // - validate authenticated doctor ownership
    }
}
