package com.example.doctorhouse.domain.port.in;

import com.example.doctorhouse.domain.model.dto.command.RegisterAppointmentEvolutionCommand;

public interface RegisterAppointmentEvolutionUseCase {
    void registerEvolution(RegisterAppointmentEvolutionCommand command);
}
