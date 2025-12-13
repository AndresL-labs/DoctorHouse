package com.example.doctorhouse.domain.port.out;

import com.example.doctorhouse.domain.model.AppointmentEvolution;

public interface AppointmentEvolutionRepositoryPort {
    AppointmentEvolution save(AppointmentEvolution evolution);
}
