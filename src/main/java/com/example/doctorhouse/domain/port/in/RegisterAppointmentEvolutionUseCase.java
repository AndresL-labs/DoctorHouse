package com.example.doctorhouse.domain.port.in;

import com.example.doctorhouse.domain.model.AppointmentEvolution;
import com.example.doctorhouse.domain.model.dto.command.RegisterAppointmentEvolutionCommand;

public interface RegisterAppointmentEvolutionUseCase {
    AppointmentEvolution registerEvolution(RegisterAppointmentEvolutionCommand command);
}
