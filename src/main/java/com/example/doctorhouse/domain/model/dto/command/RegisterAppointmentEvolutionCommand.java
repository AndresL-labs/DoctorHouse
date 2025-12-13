package com.example.doctorhouse.domain.model.dto.command;

public record RegisterAppointmentEvolutionCommand(
        Long appointmentId,
        String bloodPressure,
        Integer heartRate,
        Double temperature,
        Integer oxygenSaturation,
        Double weightKg,
        String diagnosis,
        String observations
) {}
