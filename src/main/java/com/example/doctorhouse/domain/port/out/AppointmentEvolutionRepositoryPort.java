package com.example.doctorhouse.domain.port.out;

import com.example.doctorhouse.domain.model.AppointmentEvolution;

import java.util.Optional;

public interface AppointmentEvolutionRepositoryPort {
    AppointmentEvolution save(AppointmentEvolution evolution);
    
    Optional<AppointmentEvolution> findByAppointmentId(Long appointmentId);
}
